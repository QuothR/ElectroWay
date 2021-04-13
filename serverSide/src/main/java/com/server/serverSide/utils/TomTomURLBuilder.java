package com.server.serverSide.utils;

import com.server.serverSide.model.Car;
import com.server.serverSide.model.Coords;
import com.server.serverSide.model.RouteData;

import java.net.MalformedURLException;
import java.net.URL;

public class TomTomURLBuilder {
    private static final String tomTomApiKey = "XebWrrdCuGYkHvE9hXSgnnrVIr73EWtA";
    private static final String baseURL = "https://api.tomtom.com";

    private TomTomURLBuilder() { }

    public static URL generateSearchURL(Integer limit, String query) throws MalformedURLException {
        StringBuilder stringBuilder = new StringBuilder(baseURL);
        stringBuilder.append("/search/2/search/");
        stringBuilder.append(query);
        stringBuilder.append(".json?typeahead=true&");
        if (limit != null) {
            stringBuilder.append("limit=");
            stringBuilder.append(limit.toString());
            stringBuilder.append("&");
        }
        stringBuilder.append("key=");
        stringBuilder.append(tomTomApiKey);
        return new URL(stringBuilder.toString());
    }

    public static URL generateCalculateRouteURL(RouteData routeData) throws MalformedURLException {
        StringBuilder stringBuilder = new StringBuilder(baseURL);
        stringBuilder.append("/routing/1/calculateRoute/");

        // Add locations.
        if(routeData.getLocationsCoords() != null) {
            if(routeData.getLocationsCoords().size() == 0) {
                // Error, required field.
                return new URL("...");
            }
            // More checks....
            for(int i = 0; i < routeData.getLocationsCoords().size() - 1; i++) {
                stringBuilder.append(routeData.getLocationsCoords().get(i).getLat());
                stringBuilder.append("%2C");
                stringBuilder.append(routeData.getLocationsCoords().get(i).getLon());
                stringBuilder.append("%3A");
            }

            // Add the last location.
            stringBuilder.append(routeData.getLocationsCoords().get(routeData.getLocationsCoords().size() - 1).getLat());
            stringBuilder.append("%2C");
            stringBuilder.append(routeData.getLocationsCoords().get(routeData.getLocationsCoords().size() - 1).getLon());
        }
        else {
            // Error, required field.
            return new URL("...");
        }

        // Content-Type.
        stringBuilder.append("/json?");

        // Add routeType.
        if(routeData.getRouteType() != null) {
            stringBuilder.append("routeType=");
            stringBuilder.append(routeData.getRouteType());
            stringBuilder.append("&");
        }

        // Add avoid.
        if(routeData.getAvoid() != null) {
            stringBuilder.append("avoid=");
            stringBuilder.append(routeData.getAvoid());
            stringBuilder.append("&");
        }

        // Add travelMode.
        if(routeData.getTravelMode() != null) {
            stringBuilder.append("travelMode=");
            stringBuilder.append(routeData.getTravelMode());
            stringBuilder.append("&");
        }

        // Add car details.
        Car car = routeData.getCar();
        if(car != null) {

            // Add vehicle max speed.
            if(car.getVehicleMaxSpeed() != null) {
                stringBuilder.append("vehicleMaxSpeed=");
                stringBuilder.append(car.getVehicleMaxSpeed());
                stringBuilder.append("&");
            }

            // Add engine type. Electric by default.
            stringBuilder.append("vehicleEngineType=electric&");

            // Add constantSpeedConsumptionInkWhPerHundredkm.
            if(car.getConstantSpeedConsumptionInkWhPerHundredkm() != null) {
                stringBuilder.append("constantSpeedConsumptionInkWhPerHundredkm=");
                for(int i = 0; i < car.getConstantSpeedConsumptionInkWhPerHundredkm().size() - 1; i++) {
                    stringBuilder.append(car.getConstantSpeedConsumptionInkWhPerHundredkm().get(i).getSpeed());
                    stringBuilder.append("%2C");
                    stringBuilder.append(car.getConstantSpeedConsumptionInkWhPerHundredkm().get(i).getConsumptionRate());
                    stringBuilder.append("%3A");
                }

                // Add the last constSpeed...
                stringBuilder.append(car.getConstantSpeedConsumptionInkWhPerHundredkm().get(car.getConstantSpeedConsumptionInkWhPerHundredkm().size() - 1).getSpeed());
                stringBuilder.append("%2C");
                stringBuilder.append(car.getConstantSpeedConsumptionInkWhPerHundredkm().get(car.getConstantSpeedConsumptionInkWhPerHundredkm().size() - 1).getConsumptionRate());
                stringBuilder.append("&");
            }

            // Add current Charge.
            if(car.getCurrentChargeInkWh() != null) {
                stringBuilder.append("currentChargeInkWh=");
                stringBuilder.append(car.getCurrentChargeInkWh());
                stringBuilder.append("&");
            }

            // Add max charge Charge.
            if(car.getMaxChargeInkWh() != null) {
                stringBuilder.append("maxChargeInkWh=");
                stringBuilder.append(car.getMaxChargeInkWh());
                stringBuilder.append("&");
            }

            // Add auxiliary Ink.
            if(car.getAuxiliaryInkW() != null) {
                stringBuilder.append("auxiliaryPowerInkW=");
                stringBuilder.append(car.getAuxiliaryInkW());
                stringBuilder.append("&");
            }
        }

        stringBuilder.append("key=");
        stringBuilder.append(tomTomApiKey);

        return new URL(stringBuilder.toString());
    }
}
