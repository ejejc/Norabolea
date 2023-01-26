package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.repository.airschedule.AirScheduleRepository;
import com.example.jpamaster.flight.web.dto.res.AirScheduleSearchResponseDto;
import com.example.jpamaster.flight.web.dto.req.AirScheduleSearchRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AriScheduleService {

    private final AirScheduleRepository airScheduleRepository;

    @Transactional(readOnly = true)
    public Slice<AirScheduleSearchResponseDto> searchAirScheduleByCondition(
        AirScheduleSearchRequestDto dto) {

        return airScheduleRepository.findAirScheduleBySearch(dto);
    }

}
