package com.example.electrowayfinal.utils.Routing.structures;

import com.example.electrowayfinal.models.Consumption;
import com.example.electrowayfinal.service.OpenWeatherService;
import com.example.electrowayfinal.service.TomTomService;
import com.example.electrowayfinal.utils.Routing.functions.RoutingFunctions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SpeedConsumptionImprover {
    public static List<Consumption> getNewSCR(RouteData routeData) {
        //  Extragem valorile SCR-ului actual.
        List<Consumption> SCR = routeData.getConstantSpeedConsumptionInkWhPerHundredkm();

        /*
           Pentru ca ruta pe care o vom genera(luand in considerare baza noastra de date) nu va fi foarte
           diferita de cea generata de TomTom, in calculul impactului pe care il au factorii externi in
           modificarea SCR-ului, vom lua in considerare puncte de pe ruta originala generata de TomTom.
           (Valorile temperaturii de pe ruta originala nu vor fi foarte diferite de valorile temperaturii
           de pe ruta pe care o construim noi).
        */

        // Daca obtinem eroare, nu dam throw, continuam cu valorile vechi.
        ExternalFactorsData externalFactors;
        try {
            //  Generarea rutei originale.
            JSONObject initialRoute = TomTomService.getRoute(routeData);
            //  Extragerea punctelor care reprezinta ruta.
            JSONArray initialRoutePoints = TomTomService.getRoutePoints(initialRoute);

            //  Obtinerea criteriilor pentru imbunatatirea SCR-ului.
            externalFactors = OpenWeatherService.getExternalFactors(
                    RoutingFunctions.jsonArrayToList(initialRoutePoints));

        } catch (Exception e) {
            return SCR;
        }

        //  Caclularea directiei generale a traseului.
        externalFactors.setBearing(
                OpenWeatherFunctions.bearingAngle(
                        routeData.getLocationsCoords().get(0),
                        routeData.getLocationsCoords().get(1)
                )
        );

        //  Calcularea noilor valori.
        calculateNewSCR(SCR, externalFactors);
        return SCR;
    }

    private static void calculateNewSCR(List<Consumption> SCR, ExternalFactorsData externalFactors) {
        //  Editarea fiecarui element din lista, tinand cont de criterii.
        for(Consumption s : SCR) {
            countTemperatureFactor(s, externalFactors);
            countWindFactor(s, externalFactors);
            countRoadConditionFactor(s, externalFactors);

            // Trunchierea la 3 zecimale a consumului.

            s.setConsumptionKwh(
                    (
                            new BigDecimal(
                                    String.valueOf(s.getConsumptionKwh())
                            ).setScale(3, RoundingMode.FLOOR)
                    ).doubleValue()
            );
        }
    }

    private static void countTemperatureFactor(Consumption s, ExternalFactorsData externalFactors) {
        Double avgTemp = externalFactors.getTemperature();

        if(avgTemp >= 40 || (avgTemp > -10 && avgTemp <= 0))
            s.setConsumptionKwh(s.getConsumptionKwh() / 5 + s.getConsumptionKwh());
        if((avgTemp > 30 && avgTemp < 40) || (avgTemp > 0 && avgTemp <= 10))
            s.setConsumptionKwh(s.getConsumptionKwh() / 10 + s.getConsumptionKwh());
        if(avgTemp <= -10)
            s.setConsumptionKwh(s.getConsumptionKwh() / 3 + s.getConsumptionKwh());
    }

    private static void countWindFactor(Consumption s, ExternalFactorsData externalFactors) {
        Double bearing = externalFactors.getBearing();
        Double avgDirection = externalFactors.getDirection();
        Double avgWind = externalFactors.getWind();

        Double aux = bearing - 180;
        if(aux < 0) {
            aux += 360;
        }

        if(aux >= avgDirection - 30 && aux <= avgDirection + 30) {
            s.setConsumptionKwh(((s.getConsumptionKwh() / 7) * (avgWind / 4.47)) + s.getConsumptionKwh());
        }

        if(bearing >= avgDirection - 30 && bearing <= avgDirection + 30) {
            s.setConsumptionKwh(s.getConsumptionKwh() - ((s.getConsumptionKwh() / 7) * (avgWind / 4.47)));
        }
    }

    private static void countRoadConditionFactor(Consumption s, ExternalFactorsData externalFactors) {
        Integer badConditionPrecent = externalFactors.getBadConditionPercent();
        Consumption rainConsumption = new Consumption();
        rainConsumption.setConsumptionKwh(s.getConsumptionKwh() + s.getConsumptionKwh() * 0.25);
        s.setConsumptionKwh(
                (rainConsumption.getConsumptionKwh() * badConditionPrecent +
                        s.getConsumptionKwh() * (100 - badConditionPrecent)) / 100
        );
    }
}


