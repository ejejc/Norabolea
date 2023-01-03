package com.example.jpamaster.flight.web;

import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.flight.service.AirportService;
import com.example.jpamaster.flight.web.dto.req.KeywordSearchConditionDto;
import com.example.jpamaster.flight.web.dto.req.RegisterAvailableAirlineRequestDto;
import com.example.jpamaster.flight.web.dto.res.AirportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/airport")
@RequiredArgsConstructor
@RestController
public class AirportController {

    private final AirportService airportService;

    @GetMapping
    public ApiResponse<List<AirportDto>> getAirportBySearchCondition(
            KeywordSearchConditionDto airportSearchConditionDto
    ) {
        return ApiResponse.createOk(airportService.getAirportBySearchCondition(airportSearchConditionDto));
    }

    @PostMapping(path = "/{airportSeq}")
    public ApiResponse<Void> registerAvailableAirline (
            @PathVariable("airportSeq") Long airportSeq,
            @RequestBody  RegisterAvailableAirlineRequestDto dto
    ) {
        airportService.registerAvailableAirline(airportSeq, dto);
        return ApiResponse.createEmptyBody();
    }

}
