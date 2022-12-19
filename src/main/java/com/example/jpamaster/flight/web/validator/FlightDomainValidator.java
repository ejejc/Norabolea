package com.example.jpamaster.flight.web.validator;

import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.domain.repository.AirplaneRepository;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Component
public class FlightDomainValidator implements ConstraintValidator<ValidDomain, Long> {

    private final AirportRepository airportRepository;
    private final AirplaneRepository airplaneRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        
        return false;
    }
}
