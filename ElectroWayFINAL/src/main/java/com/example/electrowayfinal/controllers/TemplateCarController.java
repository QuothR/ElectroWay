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
@RequestMapping("/templatecar")
@CrossOrigin(origins = "https://electrowayweb.herokuapp.com")
@Qualifier("template")
public class TemplateCarController {
    private final TemplateCarService templateCarService;

    @Autowired
    public TemplateCarController(TemplateCarService templateCarService) {
        this.templateCarService = templateCarService;
    }

    @PostMapping(path = "create")
    @ResponseStatus(HttpStatus.OK)
    public TemplateCar createTemplateCar(@RequestBody TemplateCar templateCar, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        templateCarService.createTemplateCar(templateCar, httpServletRequest);
        return templateCarService.getTemplateCar(templateCar.getId(), httpServletRequest);
    }

    @GetMapping(path = "get/{templateCarId}")
    @ResponseStatus(HttpStatus.OK)
    public TemplateCar getTemplateCar(@PathVariable("templateCarId") Long templateCarId, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        return templateCarService.getTemplateCar(templateCarId, httpServletRequest);
    }

    @GetMapping(path = "all")
    @ResponseStatus(HttpStatus.OK)
    public List<TemplateCar> getTemplateCars(HttpServletRequest httpServletRequest) throws UserNotFoundException {
        return templateCarService.getTemplateCars(httpServletRequest);
    }

    @RequestMapping(value = "update/{templateCarId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public TemplateCar updateTemplateCar(@RequestBody TemplateCar newTemplateCar, @PathVariable("templateCarId") Long templateCarId, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        return templateCarService.updateTemplateCar(newTemplateCar, templateCarId, httpServletRequest);
    }

    @DeleteMapping(path = "/delete/{templateCarId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTemplateCar(@PathVariable("templateCarId") Long templateCarId, HttpServletRequest httpServletRequest) throws UserNotFoundException {
        templateCarService.deleteTemplateCar(templateCarId, httpServletRequest);
    }
}
