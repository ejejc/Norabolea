package com.example.jpamaster.flight.web;

import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.flight.service.AirScheduleCreateService;
import com.example.jpamaster.flight.web.dto.req.AirScheduleRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/admin/air-schedule")
@RestController
public class AirScheduleController {

    private final AirScheduleCreateService airScheduleCreateService;

    @PostMapping
    public ApiResponse<Long> createAirSchedule(
            @RequestBody AirScheduleRequestDto dto
    ) {
        return ApiResponse.createOk(airScheduleCreateService.createAirSchedule(dto));
    }

    @PutMapping("/{airScheduleSeq}")
    public ApiResponse<Long> updateAirSchedule(
        @PathVariable("airScheduleSeq") Long airScheduleSeq,
        @RequestBody AirScheduleRequestDto dto
    ) {
        airScheduleCreateService.updateAirSchedule(airScheduleSeq, dto);
        return null;
    }

    @PutMapping("/{airScheduleSeq}/air-seat")
    public ApiResponse<Long> updateAirScheduleSeat() {
        return null;
    }
}
