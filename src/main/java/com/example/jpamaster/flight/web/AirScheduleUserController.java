package com.example.jpamaster.flight.web;


import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.common.exception.JpaMasterBadRequest;
import com.example.jpamaster.flight.service.AriScheduleService;
import com.example.jpamaster.flight.web.dto.req.AirScheduleSearchRequestDto;
import com.example.jpamaster.flight.web.dto.res.AirScheduleSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/air-schedule")
@RestController
public class AirScheduleUserController {

    private final AriScheduleService airScheduleService;

    @GetMapping
    public ApiResponse<Slice<AirScheduleSearchResponseDto>> searchAirScheduleByLowerCost(
        AirScheduleSearchRequestDto dto) {

        if (!dto.personCountValidation()) {
            throw new JpaMasterBadRequest("인원수를 확인해주세요.");
        }
        return ApiResponse.createOk(airScheduleService.searchAirScheduleByCondition(dto));
    }

}
