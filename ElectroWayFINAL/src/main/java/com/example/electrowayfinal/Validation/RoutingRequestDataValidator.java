package com.example.electrowayfinal.Validation;

import com.example.electrowayfinal.utils.Routing.structures.CarData;
import com.example.electrowayfinal.utils.Routing.structures.Coords;
import com.example.electrowayfinal.utils.Routing.structures.RoutingRequestData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class RoutingRequestDataValidator implements ConstraintValidator<RoutingRequestDataConstraint, RoutingRequestData> {
    private final List<String> avoidValues = Arrays.asList("tollRoads", "motorways", "ferries", "unpavedRoads", "carpools", "alreadyUsedRoads");

    @Override
    public void initialize(RoutingRequestDataConstraint routeDataConstraint) {
    }

    @Override
    public boolean isValid(RoutingRequestData routingRequestData, ConstraintValidatorContext constraintValidatorContext) {

        List<Coords> locs = routingRequestData.getLocationsCoords();
        // Check for locations.
        if(
                locs == null ||
                locs.size() != 2 ||
                locs.get(0) == null || locs.get(1) == null ||
                locs.get(0).getLat() == null || locs.get(0).getLon() == null ||
                locs.get(1).getLat() == null || locs.get(1).getLon() == null) {
            return false;
        }

        String avoid = routingRequestData.getAvoid();
        if(avoid == null || !(avoidValues.contains(avoid))) {
            return false;
        }

        CarData carData = routingRequestData.getCarData();
        if(
                carData == null ||
                (carData.getCarId() == null && carData.getCurrentChargeInkW() != null) ||
                (carData.getCarId() != null && carData.getCurrentChargeInkW() == null)) {
            return false;
        }
        return true;
    }
}