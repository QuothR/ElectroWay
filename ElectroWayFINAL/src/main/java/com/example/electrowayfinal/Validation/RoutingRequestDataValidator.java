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
        return routingRequestData != null & routingRequestData.getLocationsCoords() != null && routingRequestData.getLocationsCoords().size() == 2 &&
                (routingRequestData.getAvoid() == null || avoidValues.contains(routingRequestData.getAvoid())) &&
                ((routingRequestData.getCarData().getCarId() == null && routingRequestData.getCarData().getCurrentChargeInkW() == null) ||
                        (routingRequestData.getCarData().getCarId() != null && routingRequestData.getCarData().getCurrentChargeInkW() != null));
    }
}