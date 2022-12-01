package com.example.jpamaster.flight.web;

import com.example.jpamaster.flight.service.AirportService;
import com.example.jpamaster.flight.web.dto.req.AirportSearchCondition;
import com.example.jpamaster.flight.web.dto.res.AirportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/airports")
@RequiredArgsConstructor
@RestController
public class AirportController {

    private final AirportService airportService;

    @GetMapping
    public List<AirportDto> getAirportBySearchCondition(
            AirportSearchCondition airportSearchCondition
    ) {
        return airportService.getAirportBySearchCondition(airportSearchCondition);
    }
}
