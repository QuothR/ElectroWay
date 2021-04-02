package com.server.serverSide.controller;

import com.server.serverSide.service.LocationService;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/location")
public class LocationController {
    @GetMapping("/searchedLocation")
    public @ResponseBody JSONObject searchLocation(@RequestBody JSONObject req) throws IOException {
        return LocationService.search((Integer) req.get("limit"), (String) req.get("query"));
    }
}
