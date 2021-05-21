package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.PlugType;
import com.example.electrowayfinal.service.PlugTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/plug_type")
@CrossOrigin(origins = "http://localhost:3000")
@Qualifier("plug_type")
public class PlugTypeController {
    private final PlugTypeService plugTypeService;

    @Autowired
    public PlugTypeController(PlugTypeService plugTypeService) {
        this.plugTypeService = plugTypeService;
    }

    @PostMapping(path = "create/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public PlugType createPlugType(@RequestBody PlugType plugType, @PathVariable("carId") Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        plugTypeService.createPlugType(plugType, carId, httpServletRequest, httpServletResponse);
        return plugTypeService.getPlugType(plugType.getId(), httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "get/{plugTypeId}")
    @ResponseStatus(HttpStatus.OK)
    public PlugType getPlugType(@PathVariable("plugTypeId") Long plugTypeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return plugTypeService.getPlugType(plugTypeId, httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "all/{carId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlugType> getCarPlugTypes(@PathVariable("carId") Long carId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return plugTypeService.getCarPlugTypes(carId, httpServletRequest, httpServletResponse);
    }

    @RequestMapping(value = "update/{plugTypeId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public PlugType updatePlugType(@RequestBody PlugType newPlugType, @PathVariable("plugTypeId") Long plugTypeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return plugTypeService.updatePlugType(newPlugType, plugTypeId, httpServletRequest, httpServletResponse);
    }

    @DeleteMapping(path = "delete/{plugTypeId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlugType(@PathVariable("plugTypeId") Long plugTypeId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        plugTypeService.deletePlugType(plugTypeId, httpServletRequest, httpServletResponse);
    }
}
