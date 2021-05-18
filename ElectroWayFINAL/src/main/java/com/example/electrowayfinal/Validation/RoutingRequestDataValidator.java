package com.example.electrowayfinal.Validation;

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
        return routingRequestData.getLocationsCoords().size() == 2;
/*        return routeData != null & routeData.getLocationsCoords() != null && routeData.getLocationsCoords().size() == 2 &&
                (routeData.getAvoid() == null || avoidValues.contains(routeData.getAvoid())) &&
                ((routeData.getCarData().getCar().getId() == 0 && routeData.getCarData().getCurrentChargeInkW() == null) ||
                        (routeData.getCarData().getCar().getId() != 0 && routeData.getCarData().getCurrentChargeInkW() != null));*/
    }
}