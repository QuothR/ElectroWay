package com.server.serverSide.service;

import com.server.serverSide.model.RouteData;
import com.server.serverSide.utils.TomTomURLBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TomTomService {

    private TomTomService() {}

    public static JSONObject search(Integer limit, String query) throws IOException {
        URL url = TomTomURLBuilder.generateSearchURL(limit, query);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseStream, JSONObject.class);
    }

    public static JSONObject getRoute(RouteData routeData) throws IOException {
        URL url = TomTomURLBuilder.generateCalculateRouteURL(routeData);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseStream, JSONObject.class);
    }
}
