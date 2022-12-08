package com.example.jpamaster.flight.web;

import com.example.jpamaster.flight.service.AirportService;
import com.example.jpamaster.flight.web.dto.req.KeywordSearchConditionDto;
import com.example.jpamaster.flight.web.dto.res.AirportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/v1/airport")
@RequiredArgsConstructor
@RestController
public class AirportController {

    private final AirportService airportService;

    @GetMapping
    public List<AirportDto> getAirportBySearchCondition(
            KeywordSearchConditionDto airportSearchConditionDto
    ) {
        return airportService.getAirportBySearchCondition(airportSearchConditionDto);
    }
}
