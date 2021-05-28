package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.CarNotFoundException;
import com.example.electrowayfinal.exceptions.CurrentChargeInkWhException;
import com.example.electrowayfinal.exceptions.ImpossibleRouteException;
import com.example.electrowayfinal.models.*;
import com.example.electrowayfinal.repositories.*;
import com.example.electrowayfinal.utils.Routing.structures.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final PlugTypeRepository plugTypeRepository;
    private final TemplateCarRepository templateCarRepository;
    private final ConsumptionRepository consumptionRepository;

    @Autowired
    public RoutingService(
            CarRepository carRepository,
            StationRepository stationRepository,
            ChargingPointRepository chargingPointRepository,
            ChargingPlugRepository chargingPlugRepository,
            PlugTypeRepository plugTypeRepository,
            TemplateCarService templateCarService,
            TemplateCarRepository templateCarRepository,
            ConsumptionRepository consumptionRepository) {
        this.carRepository = carRepository;
        this.stationRepository = stationRepository;
        this.chargingPointRepository = chargingPointRepository;
        this.chargingPlugRepository = chargingPlugRepository;
        this.plugTypeRepository = plugTypeRepository;
        this.templateCarRepository = templateCarRepository;
        this.consumptionRepository = consumptionRepository;
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


        //getPlugType pentru masina primita in request
        Car car = routeData.getCar();
        List<PlugType> plugType= plugTypeRepository.findAllByCarId(car.getId());
        if(plugType.size()==0) {
            Optional<TemplateCar> templateCar = templateCarRepository.findById(routeData.getCar().getId());
            PlugType pt = new PlugType();
            pt.setPlugType(templateCar.get().getPlugType());
            plugType.add(pt);
        }

        //Statiile care au acelasi plugType ca masina primita in request(cu cea mai buna viteza de incarcare)
        for(Station station : possibleReachableStations) {
            // Obtinerea chargingPoint-urilor statiei.
            List<ChargingPoint> chargingPointList = chargingPointRepository.findChargingPointsByStation_Id(station.getId());

            // Initializez un plug cu viteza 0.
            ChargingPlug chargingPlug = new ChargingPlug();
            chargingPlug.setChargingSpeedKw(0);

            List<ChargingPlug> chargingPlugs=new ArrayList<>();
            for(ChargingPoint chargingPoint : chargingPointList){
                List<ChargingPlug> aux = chargingPlugRepository.findChargingPlugsByChargingPoint(chargingPoint);

                for(ChargingPlug chargingPlug1:aux){
                    if(checkGoodPlugType(plugType, chargingPlug1.getConnectorType())) {
                        if(chargingPlug1 != null && chargingPlug1.getChargingSpeedKw() > chargingPlug.getChargingSpeedKw()) {
                            chargingPlug = chargingPlug1;
                        }
                    }
                }
            }
            if(chargingPlug.getChargingSpeedKw() > 0) {
                stationDataList.add(new StationData(station, chargingPlug));
            }
        }

        return stationDataList;
    }

    public boolean checkGoodPlugType(List<PlugType> plugtypes, String connectorType) {
        for(PlugType pt : plugtypes) {
            if(pt.getPlugType().equals(connectorType)) {
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<Object> generateRoute(
            RoutingRequestData routingRequestData) throws CarNotFoundException, IOException, InterruptedException, ImpossibleRouteException, CurrentChargeInkWhException {
        // Obtinerea si editarea datelor necesare pentru generarea rutei.
        RouteData routeData = dataProcessing(routingRequestData);

        // TomTom's constraint.
        if(routeData.getCar().getVehicleMaxSpeed() >= 250) {
            routeData.getCar().setVehicleMaxSpeed(249);
        }

        // Crearea response-ului.
        RoutingFinalResponse finalResponse = new RoutingFinalResponse();

        // In el retinem datele de la TomTom, datele obtinute dintr-un request in isReachable.
        AuxiliarRouteUtil auxiliarRouteVar = new AuxiliarRouteUtil();

        // Daca s-a incercat ocolirea de 3 ori atunci algoritmul va fi oprit
        Integer movedAwayTimes = 3;

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

            // Determinarea celei mai convenabile statii ca timp si distanta.
            StationData reachableStation = reachableStation(
                    travelTimesAndDistances,
                    new RouteData(routeData),
                    finalResponse,
                    auxiliarRouteVar
            );

            // Masina s-a indepartat de ruta finala.
            if(reachableStation != null) {
                if(isTooFar(routeData, reachableStation)) {
                    movedAwayTimes--;
                }
            }

            // Nu putem ajunge, stuck.
            if (reachableStation == null || movedAwayTimes == 0) {
                throw new ImpossibleRouteException();
            }

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

        return ResponseEntity.status(200).body(finalResponse);
    }

    public static boolean isTooFar(RouteData routeData, StationData stationData) {
        Coords currentPoint = routeData.getLocationsCoords().get(0);
        Coords finalPoint = routeData.getLocationsCoords().get(1);
        Coords stationCoords = new Coords(
                stationData.getStation().getLatitude(),
                stationData.getStation().getLongitude());

        Double distCurrentPointToFinalPoint = calculateDistance(
                currentPoint.getLat(),
                currentPoint.getLon(),
                finalPoint.getLat(),
                finalPoint.getLon()
        );

        Double distReachableStationToFinalPoint = calculateDistance(
                stationCoords.getLat(),
                stationCoords.getLon(),
                finalPoint.getLat(),
                finalPoint.getLon()
        );

        return (distCurrentPointToFinalPoint < distReachableStationToFinalPoint);
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
                jsonArrayToList(
                        TomTomService.getRoutePoints(
                                auxiliarRouteVar.getRoute()
                        )
                )
                //null
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

    // De lucrat.
    public RouteData dataProcessing(RoutingRequestData routingRequestData) throws CarNotFoundException, CurrentChargeInkWhException {
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

        if(routeData.getCar().getBatteryCapacity() < routeData.getCurrentChargeInkWh()) {
            throw new CurrentChargeInkWhException(routeData.getCurrentChargeInkWh(), routeData.getCar().getBatteryCapacity());
        }

        // Extragerea datelor legate de constantSpeedConsumption.
        routeData.setConstantSpeedConsumptionInkWhPerHundredkm(
                getConstantSpeedConsumption(
                        new RouteData(routeData)
                )
        );

        return routeData;
    }

    /**
     * Citirea datelor masinii din baza de date.
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
            Optional<TemplateCar> templateCar = templateCarRepository.findById(carId);
            if(templateCar.isPresent()){
                Car templateCarConverted = new Car();
                //S-a gasit masina cu acel id in template
                templateCarConverted.setId(templateCar.get().getId());
                templateCarConverted.setAuxiliaryKwh(templateCar.get().getAuxiliaryKwh());
                templateCarConverted.setBatteryCapacity(templateCar.get().getBatteryCapacity());
                templateCarConverted.setChargingCapacity(templateCar.get().getChargingCapacity());
                templateCarConverted.setVehicleMaxSpeed(templateCar.get().getVehicleMaxSpeed());
                templateCarConverted.setYear(templateCar.get().getYear());
                templateCarConverted.setModel(templateCar.get().getModel());
                return templateCarConverted;
            }
            else {
                throw new CarNotFoundException(carId);
            }
        }
    }

    // Extragerea constant speed consumption-ului si editarea lui in functie de factori externi.
    public List<Consumption> getConstantSpeedConsumption(RouteData routeData) {
        // Editarea constantSpeedConsumption-ului in functie de factorii externi.

        // Extragerea Consumption-ului din baza de date.
        routeData.setConstantSpeedConsumptionInkWhPerHundredkm(
                getConsumption(routeData.getCar())
        );

        // Editarea constantSpeedConsumption-ului in functie de factorii externi.
        return SpeedConsumptionImprover.getNewSCR(new RouteData(routeData));
    }

    public List<Consumption> getConsumption(Car car) {
        List<Consumption> listConsumption = consumptionRepository.findAllByCarId(car.getId());
        if(listConsumption.size() == 0) {
            Consumption consumption = new Consumption();
            consumption.setSpeed(50);
            consumption.setConsumptionKwh(car.getBatteryCapacity() / 3);

            listConsumption.add(consumption);
        }

        return listConsumption;
    }

    public ResponseEntity<Object> convertToShortAnswer(ResponseEntity<Object> currentResponse) {
        if(currentResponse.getStatusCode() == HttpStatus.OK) {
            RoutingFinalResponse finalResponse = (RoutingFinalResponse) currentResponse.getBody();

            // Concatenam toate punctele din finalResponse.
            List<Coords> allPoints = new ArrayList<>();

            for(int i = 0; i < finalResponse.getLegs().size() - 1; i++) {
                List<Coords> currentLegPoints = finalResponse.getLegs().get(i).getPoints();

                // Scoatem ultimul element din lista i pentru ca el coincide cu primul element din lista i + 1.
                currentLegPoints.remove(currentLegPoints.size() - 1);
                allPoints.addAll(currentLegPoints);
            }

            // Adaugam si ultima lista.
            allPoints.addAll(
                    finalResponse.getLegs().get(finalResponse.getLegs().size() - 1).getPoints()
            );

            return ResponseEntity.status(200).body(
                    Map.of(
                            "totalTravelTime", finalResponse.getTotalTravelTime(),
                            "totalTravelDistance", finalResponse.getTotalTravelDistance(),
                            "totalTravelPrice", finalResponse.getTotalTravelPrice(),
                            "points", allPoints
                    )
            );
        }
        else {
            return currentResponse;
        }
    }
}