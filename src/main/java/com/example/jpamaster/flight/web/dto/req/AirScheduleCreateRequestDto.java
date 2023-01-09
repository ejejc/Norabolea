package com.example.jpamaster.flight.web.dto.req;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class AirScheduleCreateRequestDto {

    private Long airplaneSeq;
    private Long fromAirportSeq;
    private Long toAirportSeq;
    private String expectedTakeoffDate;
    private String expectedTakeoffTime;

    private Set<SeatRegisterRequestDto> seatRegisterRequestDtos = new HashSet<>();
}
