package com.example.jpamaster.flight.domain.repository.airschedule;

import com.example.jpamaster.flight.domain.entity.AirSchedule;
import com.example.jpamaster.flight.web.dto.req.AirScheduleSearchRequestDto;
import org.springframework.data.domain.Slice;

public interface AirScheduleCustomRepository {

    Slice<AirSchedule> findAirScheduleBySearch(AirScheduleSearchRequestDto dto);
}
