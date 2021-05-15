package com.example.electrowayfinal.service;

import com.example.electrowayfinal.exceptions.UserNotFoundException;
import com.example.electrowayfinal.exceptions.WrongAccessException;
import com.example.electrowayfinal.exceptions.WrongPrivilegesException;
import com.example.electrowayfinal.exceptions.WrongUserInServiceException;
import com.example.electrowayfinal.models.Favourite;
import com.example.electrowayfinal.models.Role;
import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.FavouriteRepository;
import com.example.electrowayfinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final UserService userService;
    private final StationService stationService;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository, UserService userService, StationService stationService) {
        this.favouriteRepository = favouriteRepository;
        this.userService = userService;
        this.stationService = stationService;
    }

    public void createFavourite(Favourite favourite, Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);
        favourite.setUser(user);

        Station station = stationService.getCurrentStation(stationId, httpServletRequest, httpServletResponse);
        favourite.setStation(station);

        favouriteRepository.save(favourite);
    }

    public Favourite getFavourite(Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws WrongUserInServiceException, WrongAccessException, UserNotFoundException {
        checkUserAndStation(httpServletRequest, httpServletResponse);

        return favouriteRepository.getOne(stationId);
    }

    public Favourite getFavourite(Long stationId, Long favouriteId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws WrongUserInServiceException, WrongAccessException, UserNotFoundException {
        checkUserAndStation(httpServletRequest, httpServletResponse);

        Optional<Favourite> optionalFavourite = favouriteRepository.findFavouriteByStationIdAndId(stationId, favouriteId);
        if (optionalFavourite.isEmpty()) {
            throw new WrongAccessException("Wrong favourite in station!");
        }
        return optionalFavourite.get();
    }

    public List<Favourite> getFavourites(Long stationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException, WrongAccessException {
        checkUserAndStation(httpServletRequest, httpServletResponse);

        return favouriteRepository.findAllByStationId(stationId);
    }

    public void deleteFavourite(Long stationId, Long favouriteId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException, WrongAccessException {
        checkUserAndStation(httpServletRequest, httpServletResponse);

        Optional<Favourite> optionalFavourite = favouriteRepository.findFavouriteByStationIdAndId(stationId, favouriteId);
        if (optionalFavourite.isEmpty()) {
            throw new WrongAccessException("Wrong favourite in station!");
        }
        favouriteRepository.deleteById(favouriteId);
    }

    private void checkUserAndStation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UserNotFoundException {
        User user = JwtUtil.getUserFromToken(userService, secret, httpServletRequest);

        if (!user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).contains("ROLE_OWNER")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new WrongPrivilegesException("Can't access station without being a station owner!");
        }
    }
}
