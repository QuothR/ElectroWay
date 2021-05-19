package com.example.electrowayfinal.utils.Routing.functions;

import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.service.TomTomService;
import com.example.electrowayfinal.utils.Routing.structures.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class RoutingFunctions {
    /**
     * Functie ajutatoare care converteste un JSONArray la un Array de Coords.
     * @param points - Un JSONArray de coordonate, array primit de la TomTom. Punctele formeaza un polyline.
     * @return JSONArray convertit la o lista de coordonate.
     */
    public static List<Coords> jsonArrayToList(JSONArray points) {
        List<Coords> coordsList = new ArrayList<>();
        for (int i = 0; i < points.length(); i++) {
            Coords coords = new Coords();
            coords.setLat(
                    points.getJSONObject(i).getDouble("latitude")
            );
            coords.setLon(
                    points.getJSONObject(i).getDouble("longitude")
            );
            coordsList.add(coords);
        }
        return coordsList;
    }

    /**
     * Functie ajutatoare care converteste gradele in radiani.
     * @param deg - Grade.
     * @return Echivalentul gradelor in radiani.
     */
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * Functie ajutatoare care converteste radianii in grade.
     * @param rad - Radiani.
     * @return Echivalentul radianilor in grade.
     */
    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    /**
     * Functie ajutatoare care calculeaza distanta dintre doua puncte geografice.
     * @param lat1 - latitudinea primului punct.
     * @param lon1 - longitudinea primului punct.
     * @param lat2 - latitudinea celui de-al doilea punct.
     * @param lon2 - longitudinea celui de-al doilea punct.
     * @return Lungimea traseului in linie dreapta de la (lat1, lon1) la (lat2, lon2).
     */
    public static Double calculateDistance(
            double lat1, double lon1,
            double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        dist = dist * 1000;
        return dist;
    }

    /**
     * Functie ajutatoare care calculeaza chargingTime-ul in functie de capacitatea de incarcare statiei, capacitatea de
     * incarcare a masinii si capacitatea bateriei masinii.
     * @param car - Obiect ce contine datele masinii. Ofera: capacitatea de incarcare a masinii si capacitatea bateriei masinii.
     * @param station - Obiect ce contine datele statiei. Ofera: capacitatea de incarcare statiei.
     * @return Timpul de incarcare a masinii. Calculat in ore!
     */
    public static Double calculateChargingTime(Car car, StationData station) {
        // Determinam care capacitate de incarcare este mai mica. Pentru ca aia va reprezenta adevarata viteza de incarcare.
        Double actualChargingSpeed = Math.min(car.getChargingCapacity(), station.getChargingPlug().getChargingSpeedKw());

        return car.getBatteryCapacity() / actualChargingSpeed;
    }

    /**
     * Functie care determina daca o ruta poate fi parcursa cu resursele curente. Folosind "auxiliarRouteVar" trimite
     * alte date legate de ruta.
     * @param routeData - Contine datele necesare pentru request-ul la TomTom.
     * @param auxiliarRouteVar - Parametru pentru a trimite mai departe datele rutei generate de TomTom.
     * @return True - ruta poate fi parcursa.
     *         False - ruta nu poate fi parcursa.
     */
    public static boolean isReachable(
            RouteData routeData,
            AuxiliarRouteUtil auxiliarRouteVar) throws IOException, InterruptedException {
        // Obtinerea rutei de la TomTom.
        JSONObject route = TomTomService.getRoute(routeData);

        /*
            Pentru ca nu folosim varianta cu plata a API-ului, exista o limita de request-uri pe care o poti face pe secunda.
            De aceea vom face o pauza de 0.2 secunde intre request-uri.
            Asta o sa incetineasca tot procesul, trebuie cumparata o cheie care sa ofere request-uri nelimitate.
        */
        Thread.sleep(200);

        // Salvarea datelor primite de la TomTom. Le vom folosi la construirea response-ului si la alte task-uri.
        if(auxiliarRouteVar != null) {
            auxiliarRouteVar.setRoute(route);
        }

        // Daca nu avem date legate de masina e automat reachable si vor returna direct true.
        if(routeData.getCar() == null || routeData.getCurrentChargeInkWh() == null) {
            return true;
        }

        // Verificam daca putem ajunge la destinatie cu nivelul actual de energie.
        Double currentChargeInkWh = routeData.getCurrentChargeInkWh();
        Double batteryConsumptionInkWh = TomTomService.getBatteryConsumptionInkWh(route);

        return (currentChargeInkWh > batteryConsumptionInkWh);
    }

    /**
     * Functie ajutatoare pentru determinarea timpului.
     * @param distance
     * @param speed
     * @return timpul in functie de distanta si viteza.
     */
    public static Double getTimeByDistanceAndSpeed(Double distance, Double speed) {
        return distance/speed;
    }

    /**
     * Cand o ruta dintre doua puncte nu poate fi parcursa atunci se va cauta o statie la care masina trebuie sa opreasca
     * pentru a face plinul. Statia va fi aleasa in functie de un timp total. Timp total compus din: timpul necesar parcurgerii
     * distantei de la A la Statie IN LINIE DREAPTA, timpul necesar parcurgerii distantei de la Statie la "reachablePoint"
     * IN LINIE DREAPTA si timpul necesar reincarcarii la maxim a bateriei la statia respectiva.
     * De ce "IN LINIE DREAPTA"? Pentru a calcula timpul parcurgerii unei rute(nu in linie dreapta) ar fi nevoie de un request
     * la TomTom. Request-urile sunt foarte costisitoare din punctul de vedere al timpului, de aceea am decis sa folosim traseul
     * in linie dreapta. Timpul total va fi destul de precis in final, nu cel mai precis, dar castigul adus vitezei de determinare
     * al statiei este net superior.
     * @param reachablePoint - Deoarece ruta dintre A si B nu poate fi parcursa cu resursele curente, vom determina un punct
     *                       intermediar C de pe ruta, punct ce este reachable din A. Punct necesar calcularii unei distante de la
     *                       Statie la el.
     * @param stations - O lista a statiilor. Statii filtrate in functie de mai multe criterii tehnice si in functie de distanta de
     *                 la statie la punctul A(Daca nu putem ajunge in linie dreapta de la A la Statie atunci sigur nu vom putea ajunge
     *                 facand ocolisuri).
     * @param routeData - Contine datele necesare calculului distantelor, timpilor si a altor lucruri.
     * @return Un Map de la o statie la o pereche <timpul total, distanta de la Statie la reachablePoint>. Avem nevoie si
     * de distanta pentru a nu alege mereu statii mult prea apropiate.
     */
    public static Map<StationData, Pair<Double, Double>> stationsTravelTimesAndDistances(
            Coords reachablePoint,
            List<StationData> stations,
            RouteData routeData
    ) throws IOException, InterruptedException {
        Map<StationData, Pair<Double, Double>> travelTimesAndDistances = new LinkedHashMap<>();

        // Obtinem coordonatele punctului A.
        Coords pointA = routeData.getLocationsCoords().get(0);

        // Consideram o viteza medie pentru calculul timpului.
        Double speed = 60.0;

        // Pentru fiecare statie obtinem datele necesare pentru <timpul total, distanta de la Statie la reachablePoint>.
        for(StationData station : stations) {
            Double currentStationTravelTime = 0.0;

            // ++++++++++ A ---> statie_curenta ++++++++++
            // Calculam distanta geografica, in linie dreapta, de la A la statie_curenta.
            Double distAToCurrentStation = calculateDistance(
                    pointA.getLat(),
                    pointA.getLon(),
                    station.getStation().getLatitude(),
                    station.getStation().getLongitude()
            ) / 1000; // "/ 1000" ca sa fie in km

            // Calculam timpul pentru parcurgerea acestei distante, timp in functie de o viteza si il adaugam la rezultatul final.
            currentStationTravelTime += getTimeByDistanceAndSpeed(distAToCurrentStation, speed);

            // ++++++++++ statie_curenta ---> reachablePoint ++++++++++
            // Calculam distanta geografica, in linie dreapta, de la statie_curenta la reachablePoint.
            Double distCurrentStationToReachablePoint = calculateDistance(
                    station.getStation().getLatitude(),
                    station.getStation().getLongitude(),
                    reachablePoint.getLat(),
                    reachablePoint.getLon()
            ) / 1000; // "/ 1000" ca sa fie in km;

            // Calculam timpul pentru parcurgerea acestei distante, timp in functie de o viteza si il adaugam la rezultatul final.
            currentStationTravelTime += getTimeByDistanceAndSpeed(distCurrentStationToReachablePoint, speed);

            // Calculam chargingTime-ul si il adaugam la rezultatul final.
            Double chargingTime = calculateChargingTime(routeData.getCar(), station);
            currentStationTravelTime += chargingTime;

            // Adaugam statia si datele acesteia.
            travelTimesAndDistances.put(station, new Pair<>(currentStationTravelTime, distCurrentStationToReachablePoint));
        }

        // Sortam map-ul dupa timpul total.
        Map<StationData, Pair<Double, Double>> sortedMap = new LinkedHashMap<>();
        travelTimesAndDistances.entrySet()
                .stream()
                .sorted(Comparator.comparing(o -> o.getValue().getFirst()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        return sortedMap;
    }

    /**
     * Determinarea unui punct intermediar de pe ruta de la A la B, punct reachable din A. Punct folosind in determinarea
     * statiilor la care masina se va opri. Chiar daca depth-ul recursiei este limitat, acest punct reachable va fi destul
     * de indepartat datorita binary search-ului.
     * @param points - O lista de puncte(polyline style) care reprezinta ruta. Ruta generata de TomTom API.
     * @param routeData - Un obiect care contine datele necesare pentru generarea rutei, vezi definitia obiectului.
     * @param lastReachablePoint - Deoarece depth-ul recursiei reprezinta o conditie de stop a functiei, trebuie sa
     *                           retinem un ultim punct la care putem ajunge, punct diferit de punctul de plecare.
     * @param depth - Depth-ul recursiei. Deoarece request-uri http sunt costisitoare, o sa avem o limita pentru recursie.
     *              Lista de puncte este foarte mare si chiar un binary search va fi costisitor avand acele request-uri http.
     * @return Un punct reachable din A cat mai indepartat de A.
     */
    public static Coords binarySearch(
            List<Coords> points,
            RouteData routeData,
            Coords lastReachablePoint,
            Integer depth
    ) throws IOException, InterruptedException {
        // Extragem coordonatele punctului A.
        Coords pointA = routeData.getLocationsCoords().get(0);

        /*
            Daca am gasit punctul cel mai reachable sau daca am gasit un reachable point diferit de A si adancimea recursiei
            e peste 10, stop.
         */
        if ((points.size() == 1) || // A ramas doar un punct in array, care de fapt e cel mai reachable.
                (depth >= 10 && // depth-ul recursiei
                        /*
                            Pe langa depth ne asiguram ca punctul de la care am pornit recursia e diferit de cel actual,
                            ca sa nu ramanem pe loc.
                        */
                        !lastReachablePoint.getLat().equals(pointA.getLat()) &&
                            !lastReachablePoint.getLon().equals(pointA.getLon()))) {
            return lastReachablePoint;
        }

        Coords middlePoint = points.get(points.size() / 2);

        //  In caz ca punctul nu este reachable, urmatoare recursie va fi cu ruta de la A la middlePoint.
        routeData.setLocationsCoords(Arrays.asList(routeData.getLocationsCoords().get(0), middlePoint));
        if (isReachable(routeData, null)) {
            // Deci punctul este reachable, acum verificam de la middlePoint la B, in caz ca exista un punct reachable si mai departat de A.
            return binarySearch(
                    points.subList(points.size() / 2, points.size()), // Extragem doar ce avem nevoie din lista.
                    routeData,
                    middlePoint,
                    depth + 1
            );
        } else {
            // Deci punctul nu este reachable, acum verificam de la A la middlePoint.
            return binarySearch(
                    points.subList(0, points.size() / 2), // Extragem doar ce avem nevoie din lista.
                    routeData,
                    lastReachablePoint,
                    depth + 1
            );
        }
    }

    /**
     * Functie care ofera o statie intermediara convenabila ca timp si distanta fara de reachablePoint. Initial se va
     * cauta in map o statie reachable care sa fie cat mai buna ca timp. Apoi, in functie de un offset a timpului, o statie
     * mai departata de punctul A poate fi aleasa daca "initial station" nu are un timp mult mai bun decat o statie mai
     * departata de A.
     * @param travelTimesAndDistances - Un Map de la o statie la o pereche <timpul total, distanta de la Statie la
     *                                reachablePoint>. Avem nevoie si de distanta pentru a nu alege mereu statii mult prea apropiate.
     * @param routeData - Un obiect care contine datele necesare pentru generarea rutei, vezi definitia obiectului.
     * @param finalResponse - Va fi returnat ca response la request-ul clientului.
     * @param auxiliarRouteVar - Parametru pentru a trimite mai departe datele rutei generate de TomTom.
     * @return Statia intermediara cea mai convenabila ca timp si distanta fata de reachablePoint.
     */
    public static StationData reachableStation(
            Map<StationData, Pair<Double, Double>> travelTimesAndDistances,
            RouteData routeData,
            RoutingFinalResponse finalResponse,
            AuxiliarRouteUtil auxiliarRouteVar
    ) throws IOException, InterruptedException {

        // Statia initiala care va fi selectata in functie de totalTravelTime.
        StationData initialStation = null;

        // Daca o statie nu e reachable, o vom elimina la a doua selectie, selectie care e in functie de distanta.
        List<StationData> toRemove = new ArrayList<>();

        Iterator i = travelTimesAndDistances.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry m = (Map.Entry) i.next();
            StationData station = (StationData)m.getKey();

            // Ne asiguram ca statia nu este luata pentru ca nu are rost sa ne intoarcem intr-o statie.
            boolean stationAlreadyTaken = false;
            for(LegData leg : finalResponse.getLegs()) {
                if(station.getStation().getId() == leg.getStationId()) {
                    stationAlreadyTaken = true;
                    break;
                }
            }

            // Modificam datele din routeData pentru a vedea daca putem ajunge de la A la "station".
            routeData.setLocationsCoords(
                    Arrays.asList(
                            routeData.getLocationsCoords().get(0),
                            new Coords(
                                    station.getStation().getLatitude(),
                                    station.getStation().getLongitude()
                            )
                    )
            );

            // Daca statia nu a fost luata deja si este reachable, atunci ea va fi initial station.
            if(!stationAlreadyTaken && isReachable(routeData, auxiliarRouteVar)) {
                initialStation = station;
                break;
            }
            else {
                // Sigur nu va fi aleasa in urmatoarele iteratii.
                toRemove.add(station);
            }
        }

        // Eliminam statiile invalide.
        for(StationData station : toRemove) {
            travelTimesAndDistances.remove(station);
        }

        // Daca nici macar la prima selectie nu am gasit ceva, nu are rost sa trecem la a doua selectie.
        if(initialStation == null) {
            return null;
        }

        // Offset-ul pentru timp. Trecut in ore!
        Double travelTimeOffset = 1.0;

        // Sortam mapa dupa distanta fata de reachablePoint. Punctele mai apropiate de reachablePoint vor fi primele.
        Map<StationData, Pair<Double, Double>> sortedMapByDistance = new LinkedHashMap<>();
        travelTimesAndDistances.entrySet()
                .stream()
                .sorted(Comparator.comparing(o -> o.getValue().getSecond()))
                .forEachOrdered(x -> sortedMapByDistance.put(x.getKey(), x.getValue()));

        i = sortedMapByDistance.entrySet().iterator(); // de scos
        while(i.hasNext()) { // de scos
            Map.Entry m = (Map.Entry) i.next();
            StationData station = (StationData) m.getKey();
            Pair<Double, Double> travelTimeAndDistance = (Pair<Double, Double>) m.getValue();
            System.out.println(station.getStation().getAddress() + "    " + travelTimeAndDistance);
        }

        i = sortedMapByDistance.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry m = (Map.Entry) i.next();
            StationData station = (StationData) m.getKey();
            Pair<Double, Double> travelTimeAndDistance = (Pair<Double, Double>) m.getValue();

            // Modificam datele din routeData pentru a vedea daca putem ajunge de la A la "station".
            routeData.setLocationsCoords(
                    Arrays.asList(
                            routeData.getLocationsCoords().get(0),
                            new Coords(
                                    station.getStation().getLatitude(),
                                    station.getStation().getLongitude()
                            )
                    )
            );

            boolean stationAlreadyTaken = false;
            for(LegData leg : finalResponse.getLegs()) {
                if(station.getStation().getId() == leg.getStationId()) {
                    stationAlreadyTaken = true;
                    break;
                }
            }

            /*
                Daca "station" este reachable si are un timp acceptabil fata de timpul lui "initial station", atunci ea
                va fi cea selectata.
             */
            if(!stationAlreadyTaken &&
                    travelTimesAndDistances.get(initialStation).getFirst() + travelTimeOffset > travelTimeAndDistance.getFirst() &&
                    isReachable(routeData, auxiliarRouteVar)) {
                return station;
            }
        }

        return null;
    }

    /**
     * Functie ajutatoare care sa returneze distanta maxima pe care o poate parcurge masina.
     * @param routeData - Contine date despre consum, nivelul curent de energie si altele..
     * @return Distanta in km pe care o poate parcurge vehiculul cu nivelul actual de energie.
     */
    public static Double getTotalElectricalRange(RouteData routeData) {
        // Distanta maxima pe care o poate parcurge masina.
        // Consumul.
        Double consumption = routeData.getConstantSpeedConsumptionInkWhPerHundredkm().get(0).getConsumptionKWh();
        Double totalElectricalRange = routeData.getCurrentChargeInkWh() / consumption * 100;
        totalElectricalRange = Math.floor(totalElectricalRange);

        return totalElectricalRange;
    }
}
