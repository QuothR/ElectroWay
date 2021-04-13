package com.example.demo.tomtom;

import com.example.demo.MockBD;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/tomtom")
public class TomTomController {

    @GetMapping
    public String getMap() {
        return "TomTom map request received.";
    }
}
