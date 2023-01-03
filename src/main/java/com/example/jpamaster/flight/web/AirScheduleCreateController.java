package com.example.jpamaster.flight.web;

import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.flight.service.AirScheduleCreateService;
import com.example.jpamaster.flight.web.dto.req.AirScheduleCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/admin/air-schedule")
@RestController
public class AirScheduleCreateController {

    private final AirScheduleCreateService airScheduleCreateService;
    @PostMapping
    public ApiResponse<Void> createAirSchedule(
            @RequestBody AirScheduleCreateRequestDto dto
    ) {

        airScheduleCreateService.createAirSchedule(dto);
        return ApiResponse.createOk(null);
    }
}
