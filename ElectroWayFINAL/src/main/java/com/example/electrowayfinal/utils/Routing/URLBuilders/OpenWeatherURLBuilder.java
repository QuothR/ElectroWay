package com.example.electrowayfinal.utils.Routing.URLBuilders;


import com.example.electrowayfinal.utils.Routing.structures.Coords;

import java.net.MalformedURLException;
import java.net.URL;

public class OpenWeatherURLBuilder {

    private static final String openWeatherApiKey = "28c399ec2712e8db1fa0facf45219748";
    private static final String baseURL = "http://api.openweathermap.org";
    private OpenWeatherURLBuilder() {}

    public static URL getLocationExternalFactorsInfo(Coords point) throws MalformedURLException {
        StringBuilder stringBuilder = new StringBuilder(baseURL);
        stringBuilder.append("/data/2.5/weather?lat=");

        // Add latitude.
        stringBuilder.append(point.getLat());

        // Add longitude.
        stringBuilder.append("&lon=");
        stringBuilder.append(point.getLon());

        // Add units.
        stringBuilder.append("&units=metric");

        // Add apikey.
        stringBuilder.append("&appid=");
        stringBuilder.append(openWeatherApiKey);

        return new URL(stringBuilder.toString());
    }

}
