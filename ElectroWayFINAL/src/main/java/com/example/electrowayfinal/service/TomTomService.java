package com.example.electrowayfinal.service;

import com.example.electrowayfinal.utils.Routing.URLBuilders.TomTomURLBuilder;
import com.example.electrowayfinal.utils.Routing.structures.RouteData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TomTomService {

    private TomTomService() {}

    /**
     * Construirea URL-ului pentru request, trimiterea request-ului, citirea si returnarea raspunsului.
     * @param routeData - Contine datele necesare pentru TomTom request.
     * @return Ruta generata de TomTom, impreuna cu alte date folositoare(timpul total, distanta totala si altele).
     */
    public static JSONObject getRoute(RouteData routeData) throws IOException {
        // Pregatirea url-ului pentru request-ul catre TomTom si trimiterea acestuia.
        URL url = TomTomURLBuilder.generateCalculateRouteURL(routeData);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        // Citirea raspunsului.
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new JSONObject(response.toString());
    }

    /**
     * Functie ajutatoare care extrage polyline-ul generat de TomTom. Polyline ce reprezinta ruta.
     * @param route - Ruta generata de TomTom, impreuna cu alte date folositoare(timpul total, distanta totala si altele).
     * @return JSONArray de coordonate.
     */
    public static JSONArray getRoutePoints(JSONObject route) {
        // ".getJSONObject(0)" - luam prima ruta din lista.
        var routes =  route.getJSONArray("routes").getJSONObject(0);

        // "getJSONObject(0)" - Nu vor fi niciodata mai mult de 2 leg-uri.
        var legs = routes.getJSONArray("legs").getJSONObject(0);

        // Extragerea coordonatelor.
        JSONArray points = legs.getJSONArray("points");

        return points;
    }

    /**
     * Functie ajutatoare care extrage consumul masinii pentru ruta data ca parametru.
     * @param route - Ruta generata de TomTom, impreuna cu alte date folositoare(timpul total, distanta totala si altele).
     * @return Consumul in kWh.
     */
    public static Double getBatteryConsumptionInkWh(JSONObject route) {
        // ".getJSONObject(0)" - luam prima ruta din lista.
        var routes =  route.getJSONArray("routes").getJSONObject(0);

        // Extragem datele generale a rutei.
        var summary = routes.getJSONObject("summary");

        // Extragem consumul.
        try {
            Double batteryConsumptionInkWh = summary.getDouble("batteryConsumptionInkWh");
            return batteryConsumptionInkWh;
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Functie ajutatoare care extrage timpul necesar parcurgerii traseului pentru ruta data ca parametru.
     * @param route - Ruta generata de TomTom, impreuna cu alte date folositoare(timpul total, distanta totala si altele).
     * @return Timpul necesar parcurgerii traseului in secunde.
     */
    public static Double getTravelTimeInSec(JSONObject route) {
        // ".getJSONObject(0)" - luam prima ruta din lista.
        var routes =  route.getJSONArray("routes").getJSONObject(0);

        // Extragem datele generale a rutei.
        var summary = routes.getJSONObject("summary");

        // Extragem timpul necesar parcurgerii traseului.
        Double travelTime = summary.getDouble("travelTimeInSeconds");

        return travelTime;
    }

    /**
     * Functie ajutatoare care extrage lungimea traseului pentru ruta data ca parametru.
     * @param route - Ruta generata de TomTom, impreuna cu alte date folositoare(timpul total, distanta totala si altele).
     * @return Lungimea traseului in metri.
     */
    public static Double getTravelDistanceInMeters(JSONObject route) {
        // ".getJSONObject(0)" - luam prima ruta din lista.
        var routes =  route.getJSONArray("routes").getJSONObject(0);

        // Extragem datele generale a rutei.
        var summary = routes.getJSONObject("summary");

        // Extragem lungimea traseului.
        Double travelDistance = summary.getDouble("lengthInMeters");

        return travelDistance;
    }
}
