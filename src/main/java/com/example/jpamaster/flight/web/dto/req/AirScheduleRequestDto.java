package com.example.jpamaster.flight.web.dto.req;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class AirScheduleRequestDto {

    private Long airplaneSeq;
    private Long fromAirportSeq;
    private Long toAirportSeq;
    private String expectedTakeoffDate;
    private String expectedTakeoffTime;

    private Set<AirScheduleSeatRequestDto> airScheduleSeatRequestDtos = new HashSet<>();

    public AirScheduleRequestDto(Long airplaneSeq, Long fromAirportSeq, Long toAirportSeq,
        String expectedTakeoffDate, String expectedTakeoffTime) {
        this.airplaneSeq = airplaneSeq;
        this.fromAirportSeq = fromAirportSeq;
        this.toAirportSeq = toAirportSeq;
        this.expectedTakeoffDate = expectedTakeoffDate;
        this.expectedTakeoffTime = expectedTakeoffTime;
    }
}
