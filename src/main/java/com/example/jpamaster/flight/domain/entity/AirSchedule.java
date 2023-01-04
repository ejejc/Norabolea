package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "air_schedule")
@Entity
public class AirSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airScheduleSeq;

    private LocalDateTime departAt;

    private LocalDateTime arriveAt;

    private Integer flightDistanceKm;

    private Integer estimatedHourSpent;

    private Integer estimatedMinuteSpent;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "airplane_seq")
    private Airplane airplane;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dept_airport_seq")
    private Airport deptAirport;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "arr_airport_seq")
    private Airport arrAirport;

    @Builder
    public AirSchedule (LocalDateTime departAt, LocalDateTime arriveAt, Integer flightDistanceKm,
                        Integer estimatedHourSpent, Integer estimatedMinuteSpent, Airplane airplane,
                        Airport deptAirport, Airport arrAirport) {
        this.departAt = departAt;
        this.arriveAt = arriveAt;
        this.flightDistanceKm = flightDistanceKm;
        this.estimatedHourSpent = estimatedHourSpent;
        this.estimatedMinuteSpent = estimatedMinuteSpent;
        this.airplane = airplane;
        this.deptAirport = deptAirport;
        this.arrAirport = arrAirport;
    }
}
