package com.example.electrowayfinal.service;

import com.example.electrowayfinal.utils.Routing.URLBuilders.OpenWeatherURLBuilder;
import com.example.electrowayfinal.utils.Routing.structures.Coords;
import com.example.electrowayfinal.utils.Routing.structures.ExternalFactorsData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static com.example.electrowayfinal.utils.Routing.structures.OpenWeatherFunctions.*;

public class OpenWeatherService {

    private OpenWeatherService() {}

    public static ExternalFactorsData getExternalFactors(List<Coords> routePoints) throws IOException, InterruptedException, ParseException, IOException, ParseException {
        //  Reset all.
        ExternalFactorsData externalFactors = new ExternalFactorsData();
        externalFactors.setTemperature(0.0);
        externalFactors.setWind(0.0);
        externalFactors.setDirection(0.0);

        /*
            Deoarece numarul punctelor poate fi de ordinul zecilor de mii, nu le vom lua pe toate
            deoarece nici nu prea are rost. De la punct la punct temperatura nu va fi foarte diferita.
            Daca le-am lua pe toate ar fi necesare prea multe request-uri care ar necesita prea mult timp.
            De aceea alegem un numar limitat de puncte.
         */
        Integer nrOfPoints;
        if(routePoints.size() <= 4000) {
            nrOfPoints = 20;
        }
        else {
            nrOfPoints = routePoints.size() / 1000; // 0.1%
        }
        //  Ca sa sarim uniform punctele.
        Integer adder = (Integer) (routePoints.size() / nrOfPoints);

        /*
            Sa stim cate puncte luam. Pare useless pt ca avem "nrOfPoints", dar adder-ul, fiind integer
            pierde din precizie asa ca numarul pe care intradevar il luam ar putea diferi, ceea ce nu
            e bine pentru o medie.
         */
        Integer pointsCounter = 0;

        //  Pentru a calcula badConditionPercent;
        Integer condition = 0;

        for(Integer counter = 0; counter + adder < routePoints.size(); counter += adder, pointsCounter++) {
            Coords point = routePoints.get(counter);

            //  Pregatirea URL-ului pentru request.
            URL url = OpenWeatherURLBuilder.getLocationExternalFactorsInfo(point);

            //  Trimitem request-ul.
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod(String.valueOf("GET"));
            conn.connect();

            // Obtinem raspunsul.
            Scanner sc = new Scanner(url.openStream());
            String inline = new String();
            while(sc.hasNext())
            {
                inline += sc.nextLine();
            }
            sc.close();
            JSONParser parse = new JSONParser();
            JSONObject jobj = (JSONObject)parse.parse(inline);

            //  Adunam temperatura pentru medie.
            externalFactors.setTemperature(
                    externalFactors.getTemperature() + getTemperature(jobj)
            );

            //  Adunam viteza vantului pentru medie.
            externalFactors.setWind(
                    externalFactors.getWind() + getWind(jobj)
            );

            // Adunam directiile vantului pentru medie.
            externalFactors.setDirection(
                    externalFactors.getDirection() + getWindDirection(jobj)
            );

            if(getCondition(jobj).equals("Rain") || getCondition(jobj).equals("Snow")) {
                condition++;
            }
        }

        //  Facerea mediilor.
        externalFactors.setTemperature(externalFactors.getTemperature() / pointsCounter);
        externalFactors.setWind(externalFactors.getWind() / pointsCounter);
        externalFactors.setDirection(externalFactors.getDirection() / pointsCounter);
        externalFactors.setBadConditionPercent(condition / pointsCounter * 100);

        // Returnarea rezultatelor.
        return externalFactors;
    }
}
