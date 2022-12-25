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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "airplane_seq")
    private Airplane airplane;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dept_airport_seq")
    private Airport deptAirport;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "arr_airport_seq")
    private Airport arrAirport;

    @Column(name = "expected_takeoff_at")
    private LocalDateTime expectedTakeoffAt;

    @Column(name = "takeoff_at")
    private LocalDateTime takeoffAt;

    @Column(name = "expected_landing_at")
    private LocalDateTime expectedLandingAt;

    @Column(name = "landing_at")
    private LocalDateTime landingAt;


    @Builder(
            builderMethodName = "createAirSchedule",
            buildMethodName = "create"
    )
    public AirSchedule(Airplane airplane, Airport deptAirport, Airport arrAirport,
                       LocalDateTime expectedTakeoffAt, LocalDateTime expectedLandingAt) {
        this.airplane = airplane;
        this.deptAirport = deptAirport;
        this.arrAirport = arrAirport;
        this.expectedTakeoffAt = expectedTakeoffAt;
        this.expectedLandingAt = expectedLandingAt;
    }
}
