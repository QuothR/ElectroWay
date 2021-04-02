package com.server.serverSide.controller;

import com.server.serverSide.model.RouteData;
import com.server.serverSide.service.LocationService;
import com.server.serverSide.service.RoutingService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/routing")
public class RoutingController {
    @GetMapping("/route")
    public @ResponseBody JSONObject getRoute(@RequestBody RouteData routeData) throws IOException {
        return RoutingService.generateRoute(routeData);
    }
}
