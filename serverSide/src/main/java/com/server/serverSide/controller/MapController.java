package com.server.serverSide.controller;

import com.server.serverSide.dataBase.StationsDB;
import com.server.serverSide.model.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MapController {

    @Value("${tomtom.apikey}")
    private String tomTomApiKey;

    private StationsDB stationsDB = new StationsDB();
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("apikey", tomTomApiKey);
        model.addAttribute("coolLocations", stationsDB.getLocationz());
        return "home";
    }

    private List<Location> coolLocations() {
        return List.of(
                new Location("Brasov", "Brasov", "Romania", "Brasov", 21.22, 23.22),
                new Location("Brasov", "Brasov", "Romania", "Brasov", 21.22, 23.22)
        );
    }

}
