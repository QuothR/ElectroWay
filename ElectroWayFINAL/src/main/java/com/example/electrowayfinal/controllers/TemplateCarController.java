package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.TemplateCar;
import com.example.electrowayfinal.service.TemplateCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Qualifier("template")
public class TemplateCarController {
    private final TemplateCarService templateCarService;

    @Autowired
    public TemplateCarController(TemplateCarService templateCarService) {
        this.templateCarService = templateCarService;
    }

    @PostMapping(path = "tcar")
    @ResponseStatus(HttpStatus.OK)
    public TemplateCar createCar(@RequestBody TemplateCar templateCar, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        templateCarService.createTemplateCar(templateCar, httpServletRequest);
        return templateCarService.getTemplateCar(templateCar.getId(), httpServletRequest);
    }

    @GetMapping(path = "tcars")
    @ResponseStatus(HttpStatus.OK)
    public List<TemplateCar> getUserCars(HttpServletRequest httpServletRequest) throws UserNotFoundException {
        return templateCarService.getTemplateCars(httpServletRequest);
    }

    @DeleteMapping(path = "/delete/tcar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        templateCarService.deleteTemplateCar(id, httpServletRequest);
    }
}
