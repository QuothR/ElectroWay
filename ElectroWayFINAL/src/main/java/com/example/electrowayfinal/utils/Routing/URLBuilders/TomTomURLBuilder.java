package com.example.electrowayfinal.utils.Routing.URLBuilders;

import com.example.electrowayfinal.models.Car;
import com.example.electrowayfinal.utils.Routing.structures.RouteData;

import java.net.MalformedURLException;
import java.net.URL;

public class TomTomURLBuilder {
    private static final String tomTomApiKey = "WJ8s7PREG7SxRMtQTZaS6c0kyLjO5lfa";
    private static final String baseURL = "https://api.tomtom.com";

    // Alte informatii pe care le setam cum vrem noi de pe backend.
    private static final Boolean computeBestOrder = false;
    private static final String routeType = "shortest";
    private static final String travelMode = "car";
    private static final String vehicleEngineType = "electric";

    private TomTomURLBuilder() { }

    /**
     * Folosind datele din routeData se construieste URL-ul.
     * More about TomTom Route URL: https://developer.tomtom.com/content/routing-api-explorer
     * @param routeData - Contine datele necesare pentru construirea URL-ului.
     * @return URL-ul care va fi folosit la request.
     * @throws MalformedURLException
     */
    public static URL generateCalculateRouteURL(RouteData routeData) throws MalformedURLException {
        StringBuilder stringBuilder = new StringBuilder(baseURL);
        stringBuilder.append("/routing/1/calculateRoute/");

        // Add locations.
        int i;
        for( i = 0; i < routeData.getLocationsCoords().size() - 1; i++) {
            stringBuilder.append(routeData.getLocationsCoords().get(i).getLat());
            stringBuilder.append("%2C");
            stringBuilder.append(routeData.getLocationsCoords().get(i).getLon());
            stringBuilder.append("%3A");
        }

        // Add the last location.
        stringBuilder.append(routeData.getLocationsCoords().get(i).getLat());
        stringBuilder.append("%2C");
        stringBuilder.append(routeData.getLocationsCoords().get(i).getLon());

        // Content-Type.
        stringBuilder.append("/json?");

        // Add routeType.
        stringBuilder.append("routeType=");
        stringBuilder.append(routeType);
        stringBuilder.append("&");

        // Add avoid.
        if(routeData.getAvoid() != null) {
            stringBuilder.append("avoid=");
            stringBuilder.append(routeData.getAvoid());
            stringBuilder.append("&");
        }

        // Add travelMode.
        stringBuilder.append("travelMode=");
        stringBuilder.append(travelMode);
        stringBuilder.append("&");

        // Add car details.
        Car car = routeData.getCar();
        if(car != null) {
            // Add vehicle max speed.
            stringBuilder.append("vehicleMaxSpeed=");
            stringBuilder.append(car.getVehicleMaxSpeed());
            stringBuilder.append("&");

            // Add engine type. Electric by default.
            stringBuilder.append("vehicleEngineType=");
            stringBuilder.append(vehicleEngineType);
            stringBuilder.append("&");

            // Add constantSpeedConsumptionInkWhPerHundredkm.
            stringBuilder.append("constantSpeedConsumptionInkWhPerHundredkm=");
            for(i = 0; i < routeData.getConstantSpeedConsumptionInkWhPerHundredkm().size() - 1; i++) {
                stringBuilder.append(routeData.getConstantSpeedConsumptionInkWhPerHundredkm().get(i).getSpeed());
                stringBuilder.append("%2C");
                stringBuilder.append(routeData.getConstantSpeedConsumptionInkWhPerHundredkm().get(i).getConsumptionKwh());
                stringBuilder.append("%3A");
            }

            // Add the last constSpeed...
            stringBuilder.append(routeData.getConstantSpeedConsumptionInkWhPerHundredkm().get(i).getSpeed());
            stringBuilder.append("%2C");
            stringBuilder.append(routeData.getConstantSpeedConsumptionInkWhPerHundredkm().get(i).getConsumptionKwh());
            stringBuilder.append("&");

            // Add current Charge.
            stringBuilder.append("currentChargeInkWh=");
            stringBuilder.append(routeData.getCurrentChargeInkWh());
            stringBuilder.append("&");

            // Add max charge Charge.
            stringBuilder.append("maxChargeInkWh=");
            stringBuilder.append(car.getBatteryCapacity());
            stringBuilder.append("&");

            // Add auxiliary Ink.
            stringBuilder.append("auxiliaryPowerInkW=");
            stringBuilder.append(car.getAuxiliaryKwh());
            stringBuilder.append("&");
        }

        stringBuilder.append("key=");
        stringBuilder.append(tomTomApiKey);

        return new URL(stringBuilder.toString());
    }
}
