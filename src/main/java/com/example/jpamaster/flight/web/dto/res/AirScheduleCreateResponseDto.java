package com.example.jpamaster.flight.web.dto.res;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AirScheduleCreateResponseDto {

    private Long airScheduleSeq;
    private LocalDateTime departAt;
    private LocalDateTime arriveAt;
    private Integer flightDistanceKm;
    private Integer estimatedHourSpent;
    private Integer estimatedMinuteSpent;

    public AirScheduleCreateResponseDto(Long airScheduleSeq, LocalDateTime departAt, LocalDateTime arriveAt,
        Integer flightDistanceKm, Integer estimatedHourSpent, Integer estimatedMinuteSpent) {
        this.airScheduleSeq = airScheduleSeq;
        this.departAt = departAt;
        this.arriveAt = arriveAt;
        this.flightDistanceKm = flightDistanceKm;
        this.estimatedHourSpent = estimatedHourSpent;
        this.estimatedMinuteSpent = estimatedMinuteSpent;
    }
}
