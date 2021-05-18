package com.example.electrowayfinal.Validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = RoutingRequestDataValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface RoutingRequestDataConstraint {
    String message() default "Invalid RouteData";
    Class<?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}
