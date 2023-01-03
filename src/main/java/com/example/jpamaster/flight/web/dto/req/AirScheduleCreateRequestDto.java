package com.example.jpamaster.flight.web.dto.req;

import com.example.jpamaster.flight.enums.FlightEnums;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AirScheduleCreateRequestDto {

    private Long airplaneSeq;
    private Long fromAirportSeq;
    private Long toAirportSeq;
    private String expectedTakeoffDate;
    private String expectedTakeoffTime;

}
