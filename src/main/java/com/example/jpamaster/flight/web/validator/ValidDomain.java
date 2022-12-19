package com.example.jpamaster.flight.web.validator;

import com.example.jpamaster.flight.enums.FlightEnums;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FlightDomainValidator.class})
@Documented
public @interface ValidDomain {
    FlightEnums.DomainType domainType() ;

    String message() default "해당 항공기가 존재하지 않습니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
