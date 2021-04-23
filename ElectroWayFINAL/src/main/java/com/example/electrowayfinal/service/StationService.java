package com.example.electrowayfinal.service;

import com.example.electrowayfinal.models.Station;
import com.example.electrowayfinal.models.User;
import com.example.electrowayfinal.repositories.StationRepository;
import com.example.electrowayfinal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

import com.example.electrowayfinal.utils.CustomJwtAuthenticationFilter;

@Service
public class StationService{
    private final StationRepository stationRepository;
    private final UserService userService;
    @Autowired
    public StationService(StationRepository stationRepository,UserService userService) {
        this.stationRepository = stationRepository;
        this.userService = userService;
    }

    public void createStation(Station station, HttpServletRequest httpServletRequest)throws Exception{
        CustomJwtAuthenticationFilter jwt = new CustomJwtAuthenticationFilter();
        JwtUtil jwtUtil = new JwtUtil();
        String jwtToken = jwt.extractJwtFromRequest(httpServletRequest);

        boolean valid = jwtUtil.validateToken(jwtToken);
        String username = jwtUtil.getUsernameFromToken(jwtToken);

        Optional<User> optionalUser = userService.getOptionalUser(username);

        if (optionalUser.isEmpty())
            throw new Exception("wrong user in station service");


        station.setUser(optionalUser.get());

        stationRepository.save(station);

    }

    public void deleteStation(Long id){
        stationRepository.deleteById(id);
    }

    public Station getStation(Long id){
     return stationRepository.getOne(id);
    }
    public List<Station> getStations(){
        return stationRepository.findAll();
    }

    public Station updateStation(Station station, Long id){
        System.out.println("in put mapping 2\n");
        int j=0;
        for(int i=0;i<getStations().size();i++){
            System.out.println("in put mapping 3\n");
            if(getStations().get(i).getId()==id){
                System.out.println("in put mapping 4\n");
                getStations().get(i).setAddress(station.getAddress());
                getStations().get(i).setLatitude(station.getLatitude());
                getStations().get(i).setLongitude(station.getLongitude());
                return getStations().get(i);

            }
        }
        return null;
    }
}
