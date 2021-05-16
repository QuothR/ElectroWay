package com.example.electrowayfinal.controllers;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.models.Favourite;
import com.example.electrowayfinal.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Qualifier("favourite")
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/favourite")
public class FavouriteController {
    private final FavouriteService favouriteService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @PostMapping(path = "create/station/{stationId}")
    @ResponseStatus(HttpStatus.OK)
    public Favourite createFavourite(@RequestBody Favourite favourite, @PathVariable("stationId") Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        favouriteService.createFavourite(favourite, stationId, httpServletRequest, httpServletResponse);
        return favouriteService.getFavourite(favourite.getId(), httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "all/station/{stationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Favourite> getStationFavourites(@PathVariable("stationId") Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return favouriteService.getFavourites(stationId, httpServletRequest, httpServletResponse);
    }

    @GetMapping(path = "{favouriteId}/station/{stationId}")
    @ResponseStatus(HttpStatus.OK)
    public Favourite getStationFavourite(@PathVariable("stationId") Long stationId, @PathVariable("favouriteId") Long favouriteId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        return favouriteService.getFavourite(favouriteId, stationId, httpServletRequest, httpServletResponse);
    }

    @DeleteMapping(path = "delete/{favouriteId}/station/{stationId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStationFavourite(@PathVariable("stationId") Long stationId, @PathVariable("favouriteId") Long favouriteId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        favouriteService.deleteFavourite(favouriteId, stationId, httpServletRequest, httpServletResponse);
    }
}
