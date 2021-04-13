package com.server.serverSide.service;

import com.server.serverSide.model.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LocationService {

    private LocationService() {}

    public static JSONObject search(Integer limit, String query) throws IOException {
        JSONObject tomTomResponse = TomTomService.search(limit, query);
        List<Object> results = (List<Object>) tomTomResponse.get("results");
        JSONArray resultJsonArray = new JSONArray();
        for (Object result : results) {
            JSONObject jsonResult = new JSONObject((Map) result);
            String name = (String) ((Map) jsonResult.get("poi")).get("name");
            String address = (String) ((Map) jsonResult.get("address")).get("freeformAddress");
            String country = (String) ((Map) jsonResult.get("address")).get("country");
            String municipality = (String) ((Map) jsonResult.get("address")).get("municipality");
            Double lat = (Double) ((Map) jsonResult.get("position")).get("lat");
            Double lon = (Double) ((Map) jsonResult.get("position")).get("lon");
            resultJsonArray.add(new Location(name, address, country, municipality, lat, lon));
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("results", resultJsonArray);
        return resultJson;
    }
}
