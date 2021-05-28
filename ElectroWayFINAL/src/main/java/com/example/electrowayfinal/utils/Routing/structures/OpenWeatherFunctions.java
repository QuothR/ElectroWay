package com.example.electrowayfinal.utils.Routing.structures;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class OpenWeatherFunctions {
    public static double getTemperature(JSONObject jobj){
        JSONObject jsonarr_1 = (JSONObject) jobj.get("main");
        double temperatura=0;
        try {
            temperatura = (Double) jsonarr_1.get("temp");
        }
        catch (ClassCastException e){
            temperatura=((Long) jsonarr_1.get("temp"))*1.0;
        }
        return temperatura;
    }

    public static double getWind(JSONObject jobj) {
        JSONObject jsonarr_1 = (JSONObject) jobj.get("wind");
        double wind=0;
        try {
            wind = (Double) jsonarr_1.get("speed");
        }
        catch (ClassCastException e){
            wind=((Long) jsonarr_1.get("speed"))*1.0;
        }
        return wind;
    }

    public static double getWindDirection(JSONObject jobj) {
        JSONObject jsonarr_1 = (JSONObject) jobj.get("wind");
        long windDirection = (long) jsonarr_1.get("deg");
        return windDirection;
    }

    public static String getCondition(JSONObject jobj){
        JSONArray jsonarr_1 = (JSONArray) jobj.get("weather");
        JSONObject jsonObject= (JSONObject) jsonarr_1.get(0);
        String condition = (String) jsonObject.get("main");
        return condition;
    }

    public static double bearingAngle(Coords location1 , Coords location2){
        // Calculul directiei unei drepte.
        double lat1 = location1.getLat() * Math.PI / 180;
        double lon1 = location1.getLon() * Math.PI / 180;

        double lat2 = location2.getLat() * Math.PI / 180;
        double lon2 = location2.getLon() * Math.PI / 180;

        double y = Math.sin(lon2 - lon1) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) -
                Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);

        double teta = Math.atan2(y, x);
        double brng = (teta * 180 / Math.PI + 360) % 360;
        return brng;
    }
}