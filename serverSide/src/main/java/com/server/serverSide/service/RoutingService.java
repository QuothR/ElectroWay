package com.server.serverSide.service;

import com.server.serverSide.model.Location;
import com.server.serverSide.model.RouteData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RoutingService {

    public static JSONObject generateRoute(RouteData routeData) throws IOException {
        /* You can call your own algorithm here and create your own
        response depending on what TomTom API returns.
         */
        return TomTomService.getRoute(routeData);
    }
}
