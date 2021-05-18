package com.example.electrowayfinal.temporaryDB;

import com.example.electrowayfinal.utils.Routing.structures.Coords;
import com.example.electrowayfinal.utils.Routing.structures.StationData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("dataBase")
public class StationsDB {
    public StationsDB() {
        /*47.16970894203321
                , 27.590858136529523
                , 46.93343031269677
                , 26.927826700373263));*/
        //,
        //,
        //,

    }
    public static List<StationData> getStations(){
        List<StationData> stations = new ArrayList<>();
        stations.add(new StationData(1L, new Coords(47.56089, 27.21099), 0.5, 15.7, "Statie Rauseni"));

        stations.add(new StationData(2L, new Coords(47.16501, 27.60433), 0.8, 3.7, "Statie Tatarasi"));

        stations.add(new StationData(3L, new Coords(47.13168, 27.69308), 0.7, 7.2, "Statie Tomesti"));

        stations.add(new StationData(4L, new Coords(47.11124, 27.60344), 0.3, 4.6, "Statie Visan"));

        stations.add(new StationData(5L, new Coords(46.97122, 27.71788), 0.75, 10.3, "Statie Dobrovat"));

        stations.add(new StationData(6L, new Coords(47.00869, 27.81951), 0.55, 6.6, "Statie Hilita"));

        stations.add(new StationData(7L, new Coords(47.04239, 27.51532), 0.55, 5.3, "Statie Mogosesti"));

        stations.add(new StationData(8L, new Coords(47.05463, 27.70686), 0.45, 4.2, "Statie Poieni"));

        stations.add(new StationData(9L, new Coords(46.73934, 27.76523), 0.5, 4.7, "Statie Valeni"));

        stations.add(new StationData(10L, new Coords(46.63711, 27.72643), 1.2, 11.0, "Statie Vaslui"));

        stations.add(new StationData(11L, new Coords(46.23187, 27.67285), 1.2, 9.2, "Statie Barlad"));

        stations.add(new StationData(12L, new Coords(45.88518, 27.45056), 0.3, 3.9, "Statie Tecuci"));

        stations.add(new StationData(13L, new Coords(45.43407, 28.03009), 0.4, 4.7, "Statie Galati"));

        stations.add(new StationData(14L, new Coords(45.48966, 26.59481), 1.1, 8.2, "Statie Lopatari"));

        stations.add(new StationData(15L, new Coords(44.58811, 27.39166), 0.2, 1.5, "Statie Slobozia"));

        stations.add(new StationData(16L, new Coords(44.30532, 26.31396), 0.5, 8.3, "Statie Frumusani"));

        stations.add(new StationData(17L, new Coords(44.55786, 28.04515), 1.0, 5.8, "Statie Topalu"));
        return stations;
    }
}
