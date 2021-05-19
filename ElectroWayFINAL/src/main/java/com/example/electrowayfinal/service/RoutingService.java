package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.CarNotFoundException;
import com.example.electrowayfinal.exceptions.ImpossibleRouteException;
import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.models.ChargingPlug;
import com.example.electrowayfinal.models.ChargingPoint;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.repositories.ChargingPlugRepository;
import com.example.electrowayfinal.repositories.ChargingPointRepository;
import com.example.electrowayfinal.repositories.StationRepository;
import com.example.electrowayfinal.utils.Routing.structures.*;
import com.example.electrowayfinal.repositories.CarRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.example.electrowayfinal.utils.Routing.functions.RoutingFunctions.*;

@Service
public class RoutingService {

    private final CarRepository carRepository;
    private final StationRepository stationRepository;
    private final ChargingPointRepository chargingPointRepository;
    private final ChargingPlugRepository chargingPlugRepository;

    @Autowired
    public RoutingService(
            CarRepository carRepository,
            StationRepository stationRepository,
            ChargingPointRepository chargingPointRepository,
            ChargingPlugRepository chargingPlugRepository) {
        this.carRepository = carRepository;
        this.stationRepository = stationRepository;
        this.chargingPointRepository = chargingPointRepository;
        this.chargingPlugRepository = chargingPlugRepository;
    }

    /**
     * Extragerea tuturor statiilor din baza de date pentru a le folosi in algoritm.
     * @return O lista cu toate statiile din baza de date.
     */
    public List<Station> getStations() {
        return stationRepository.findAll();
    }

    public List<Station> getPossibleReachableStations(RouteData routeData) {
        // Toate statiile din baza de date.
        List<Station> stationList = getStations();

        // Pentru a verifica daca am putea ajunge macar in linie dreapta la acea statie.
        Coords pointA = routeData.getLocationsCoords().get(0);
        Double totalElectricalRange = getTotalElectricalRange(routeData);

        // Construim o lista de statii la care am putea ajunge in linie dreapta.
        List<Station> possibleReachableStations = new ArrayList<>();

        for(Station station : stationList) {
            // Distanta de la A la acea statie.
            Double distAtoCurrentStation = calculateDistance(
                    pointA.getLat(),
                    pointA.getLon(),
                    station.getLatitude(),
                    station.getLongitude()
            );

            // Rotunjim.
            distAtoCurrentStation = Math.floor(distAtoCurrentStation / 1000);

            if(distAtoCurrentStation < totalElectricalRange) {
                possibleReachableStations.add(station);
            }
        }

        return possibleReachableStations;
    }

    public List<StationData> getStationsData(RouteData routeData) {

        // O lista cu statii la care am putea ajunge in linie dreapta cu nivelul actual de energie.
        List<Station> possibleReachableStations = getPossibleReachableStations(routeData);

        // O lista care contine obiecte in care retinem date despre statie, chargingPlug, ...
        List<StationData> stationDataList = new ArrayList<>();

        List<ChargingPoint> chargingPointList = new ArrayList<>();

        for(Station station : possibleReachableStations) {
            // Obtinerea chargingPoint-urilor statiei.
            chargingPointList = chargingPointRepository.findChargingPointsByStation_Id(station.getId());

            // Initializez un plug cu viteza 0.
            ChargingPlug chargingPlug = new ChargingPlug();
            chargingPlug.setChargingSpeedKw(0);

            for(ChargingPoint chargingPoint : chargingPointList) {
                // Plugul cu viteza maxima corespunzator fiecarui chargingPoint.
                ChargingPlug aux = chargingPlugRepository.findChargingPlugsByChargingPoint(chargingPoint)
                        .stream()
                        .max(Comparator.comparing(ChargingPlug::getChargingSpeedKw))
                        .orElse(null);

                // Plugul cu viteza maxima per total, pt toate chargingPoint-urile.
                if((aux != null) && (aux.getChargingSpeedKw() > chargingPlug.getChargingSpeedKw()))
                    chargingPlug = aux;
            }

            stationDataList.add(new StationData(station, chargingPlug));
        }

        return stationDataList;
    }

    public ResponseEntity<Object> generateRoute(
            RoutingRequestData routingRequestData) throws CarNotFoundException, IOException, InterruptedException, ImpossibleRouteException {
        // Obtinerea si editarea datelor necesare pentru generarea rutei.
        RouteData routeData = dataProcessing(routingRequestData);

        // Crearea response-ului.
        RoutingFinalResponse finalResponse = new RoutingFinalResponse();

        // In el retinem datele de la TomTom, datele obtinute dintr-un request in isReachable.
        AuxiliarRouteUtil auxiliarRouteVar = new AuxiliarRouteUtil();

        // Cat timp nu putem ajunge la B.
        while(!isReachable(routeData, auxiliarRouteVar)) {
            // Determinarea unui punct la care se poate ajunge, punct care sa fie cat mai aproape de B.
            JSONObject route = auxiliarRouteVar.getRoute();
            JSONArray points = TomTomService.getRoutePoints(route);

            // Determinam un punct cat mai aproape de B, care sa fie reachable din A.
            Coords reachablePoint = binarySearch(
                    jsonArrayToList(points),
                    new RouteData(routeData),
                    routeData.getLocationsCoords().get(0),
                    0
            );

            // Obtinere statii in functie de punctul A.
            List<StationData> stations = getStationsData(routeData);

            // Obtinerea timpului total si distanta in linie dreapta fata de reachablePoint pentru fiecare statie.
            Map<StationData, Pair<Double, Double>> travelTimesAndDistances = stationsTravelTimesAndDistances(
                    reachablePoint,
                    stations,
                    new RouteData(routeData)
            );

            Iterator i = travelTimesAndDistances.entrySet().iterator();
            while(i.hasNext()) { // de scos
                Map.Entry m = (Map.Entry) i.next();
                StationData station = (StationData) m.getKey();
                Pair<Double, Double> travelTimeAndDistance = (Pair<Double, Double>) m.getValue();
                System.out.println(station.getStation().getAddress() + "    " + travelTimeAndDistance);
            }
            System.out.println("");

            // Determinarea celei mai convenabile statii ca timp si distanta.
            StationData reachableStation = reachableStation(
                    travelTimesAndDistances,
                    new RouteData(routeData),
                    finalResponse,
                    auxiliarRouteVar
            );

            // Nu putem ajunge, stuck.
            if (reachableStation == null) {
                throw new ImpossibleRouteException();
            }

            System.out.println("S-a ales: " + reachableStation.getStation().getAddress());

            // adaugam leg-ul la final response.
            addDataToResponse(finalResponse, auxiliarRouteVar, reachableStation);

            // Recharge.
            Car car = routeData.getCar();
            routeData.setCurrentChargeInkWh(8 * car.getBatteryCapacity() / 10);

            // continuam cu ruta statie-B
            routeData.setLocationsCoords(
                    Arrays.asList(
                            new Coords(
                                    reachableStation.getStation().getLatitude(),
                                    reachableStation.getStation().getLongitude()
                            ),
                            routeData.getLocationsCoords().get(1)
                    )
            );
        }

        // Adaugam ultimul leg.
        addDataToResponse(finalResponse, auxiliarRouteVar, null);

        System.out.println("\n" + finalResponse);

        return ResponseEntity.status(200).body(finalResponse);
    }

    private static void addDataToResponse(
            RoutingFinalResponse finalResponse,
            AuxiliarRouteUtil auxiliarRouteVar,
            StationData stationData
    ) {
        // TravelTime in minutes.
        Double currentTravelTime = finalResponse.getTotalTravelTime();
        currentTravelTime += TomTomService.getTravelTimeInSec(auxiliarRouteVar.getRoute()) / 60;
        finalResponse.setTotalTravelTime(currentTravelTime);

        // TravelDistance in km.
        Double currentTravelDistance = finalResponse.getTotalTravelDistance();
        currentTravelDistance += TomTomService.getTravelDistanceInMeters(auxiliarRouteVar.getRoute()) / 1000;
        finalResponse.setTotalTravelDistance(currentTravelDistance);

        // Adaugarea ultimului leg.
        LegData lastLeg = new LegData();

        // Id statiei in care vom opri la finalul acestui leg.
        lastLeg.setStationId(((stationData == null) ? null : stationData.getStation().getId()));

        // Adresa statiei in care vom opri la finalul acestui leg.
        lastLeg.setAddress(((stationData == null) ? null : stationData.getStation().getAddress()));

        // Pretul statiei in care vom opri la finalul acestui leg.
        lastLeg.setPriceKw(((stationData == null) ? null : stationData.getChargingPlug().getPriceKw()));

        // Adaugarea punctelor pentru ruta.
        lastLeg.setPoints(
                /*jsonArrayToList(
                        TomTomService.getRoutePoints(
                                auxiliarRouteVar.getRoute()
                        )
                )*/
                null
        );

        // TravelPrice in u.m.
        Double currentTravelPrice = finalResponse.getTotalTravelPrice();
        Double batteryConsumption = TomTomService.getBatteryConsumptionInkWh(auxiliarRouteVar.getRoute());
        if(stationData != null) {
            Double currentStationPrice =  batteryConsumption * stationData.getChargingPlug().getPriceKw();
            currentTravelPrice += currentStationPrice;
            finalResponse.setTotalTravelPrice(currentTravelPrice);
        }

        // Trebuie sa setam datele pentru leg-ul precedent.
        if(finalResponse.getLegs().size() > 0) {
            finalResponse.getLegs().get(finalResponse.getLegs().size() - 1).setCurrentChargeInkWhAfterRecharge(batteryConsumption);
        }

        // Adaugam leg-ul pentru iteratia curenta.
        finalResponse.getLegs().add(lastLeg);

    }

    public RouteData dataProcessing(RoutingRequestData routingRequestData) throws CarNotFoundException {
        // Construim routeData. Mai intai adaugam locatiile si "avoid".
        RouteData routeData = new RouteData(routingRequestData);

        if(routingRequestData.getCarData().getCarId() == null) {
            // Ne vom folosi doar de locatii si "avoid" pentru ca nu am primit nici o masina la request.
            return routeData;
        }

        // Extragerea datelor legate de masina.
        routeData.setCar(
                getCarData(
                        routingRequestData.getCarData().getCarId()
                )
        );

        // Se adauga valoarea primita de la utilizator la "currentChargeInkWh".
        routeData.setCurrentChargeInkWh(
                routingRequestData.getCarData().getCurrentChargeInkW()
        );

        // Extragerea datelor legate de constantSpeedConsumption.
        routeData.setConstantSpeedConsumptionInkWhPerHundredkm(
                getConstantSpeedConsumption(
                        routingRequestData.getCarData().getCarId()
                )
        );

        // Editarea constantSpeedConsumption-ului in functie de factorii externi.

        return routeData;
    }

    /**
     *
     * @param carId - Id-ul masinii oferit de client la request. Id dupa care se va face cautarea in baza de date.
     * @return Car - Masina citita din baza de date.
     */
    public Car getCarData(Long carId) throws CarNotFoundException {
        /*
            Extragearea masinii din baza de date, in functie de carId.

            In cazul in care nu s-au oferit datele necesare la request sau daca acestea nu sunt valide,
            atunci obiectul "Car" din "routeData" va fi null, iar in generarea rutei nu se vor lua in
            considerare statiile(evident, neavand datele masinii nu putem determina statiile pe la care
            trebuie sa treaca).
         */

        // Incercam sa extragem obiectul "car" din baza de date.
        Optional<Car> car = carRepository.findById(carId);

        if(car.isPresent()) {
            // Daca s-a gasit o masina cu acel id in baza de date atunci se va returna.
            return car.get();
        }
        else {
            throw new CarNotFoundException(carId);
        }
    }

    public List<Consumption> getConstantSpeedConsumption(Long carId) {
        // Obtinerea constantSpeedConsumption din baza de date.
        List<Consumption> listConsumption = new ArrayList<>();
        listConsumption.add(new Consumption(50, 8.2));
        return listConsumption;
    }
}
