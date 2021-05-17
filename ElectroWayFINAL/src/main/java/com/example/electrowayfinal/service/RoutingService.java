package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.CarNotFoundException;
import com.example.electrowayfinal.exceptions.ImpossibleRouteException;
import com.example.electrowayfinal.temporaryDB.StationsDB;
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

    @Autowired
    public RoutingService(CarRepository carRepository) {
        this.carRepository = carRepository;
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
            List<StationData> stations = getStations(routeData);

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
                System.out.println(station + "    " + travelTimeAndDistance);
            }
            System.out.println("\n");

            // Determinarea celei mai convenabile statii ca timp si distanta.
            StationData reachableStation = reachableStation(
                    travelTimesAndDistances,
                    new RouteData(routeData),
                    finalResponse,
                    auxiliarRouteVar
            );

            System.out.println("S-a ales: " + reachableStation.getAddress());

            // Nu putem ajunge, stuck.
            if (reachableStation == null) {
                throw new ImpossibleRouteException();
            }

            // adaugam leg-ul la final response.
            addDataToResponse(finalResponse, auxiliarRouteVar, reachableStation);

            // Recharge.
            Car car = routeData.getCar();
            routeData.setCurrentChargeInkWh(8 * car.getMaxChargeInkWh() / 10);

            // continuam cu ruta statie-B
            routeData.setLocationsCoords(
                    Arrays.asList(
                            reachableStation.getCoords(),
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
        lastLeg.setStationId(((stationData == null) ? null : stationData.getStationId()));

        // Adresa statiei in care vom opri la finalul acestui leg.
        lastLeg.setAddress(((stationData == null) ? null : stationData.getAddress()));

        // Pretul statiei in care vom opri la finalul acestui leg.
        lastLeg.setPriceKw(((stationData == null) ? null : stationData.getPriceKw()));

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
            Double currentStationPrice =  batteryConsumption * stationData.getPriceKw();
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

    private static List<StationData> getStations(RouteData routeData) {
        // Pentru a verifica daca am putea ajunge macar in linie dreapta la acea statie.
        Coords pointA = routeData.getLocationsCoords().get(0);

        // Distanta maxima pe care o poate parcurge masina.
        // Consumul.
        Double consumption = routeData.getConstantSpeedConsumptionInkWhPerHundredkm().get(0).getConsumptionKWh();
        Double totalElectricalRange = routeData.getCurrentChargeInkWh() / consumption * 100;
        totalElectricalRange = Math.floor(totalElectricalRange);

        List<StationData> goodStations = new ArrayList<>();
        for(StationData s : StationsDB.getStations()) {
            // Distanta de la A la acea statie.
            Double distA = calculateDistance(
                    pointA.getLat(),
                    pointA.getLon(),
                    s.getCoords().getLat(),
                    s.getCoords().getLon()
            );

            // Rotunjim.
            distA = Math.floor(distA / 1000);

            if(distA <= totalElectricalRange) {
                goodStations.add(s);
            }
        }

        return goodStations;
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

        com.example.electrowayfinal.models.Car carTest = null; // de scos
        // Incercam sa extragem obiectul "car" din baza de date.
        Optional<com.example.electrowayfinal.models.Car> car = carRepository.findById(carId); // de scos
        // Optional<Car> car = carRepository.findById(carId);

        if(car.isPresent()) {
            // Daca s-a gasit o masina cu acel id in baza de date atunci se va returna.
            // return car.get();
            // TEMPORAR --------------------------------
            return new Car(
                    "Audi Q7 e-tron quattro",
                    2016L,
                    15.8,
                    3.7,
                    "Type 2",
                    230L,
                    0.05
            );
            // -----------------------------------------
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
