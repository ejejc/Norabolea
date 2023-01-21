package com.example.jpamaster.flight.service;

import com.example.jpamaster.common.ApiResponse;
import com.example.jpamaster.flight.domain.entity.AirSchedule;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import com.example.jpamaster.flight.domain.repository.airschedule.AirScheduleRepository;
import com.example.jpamaster.flight.web.dto.req.AirScheduleSearchRequestDto;
import com.example.jpamaster.flight.web.dto.res.AirScheduleSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AriScheduleService {

    private final AirScheduleRepository airScheduleRepository;
    private final AirportRepository airportRepository;

    @Transactional(readOnly = true)
    public Slice<AirScheduleSearchResponseDto> searchAirScheduleByCondition(
        AirScheduleSearchRequestDto dto) {

        Slice<AirSchedule> airScheduleBySearch = airScheduleRepository.findAirScheduleBySearch(dto);
        return null;
    }

}
