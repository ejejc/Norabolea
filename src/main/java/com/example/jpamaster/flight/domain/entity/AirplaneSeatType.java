package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.flight.enums.FlightEnums;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "airplane_seat_type")
@Entity
public class AirplaneSeatType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airplaneSeatTypeSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airplane_seq")
    private Airplane airplane;

    @Comment("좌석 종류")
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type")
    private FlightEnums.SeatType seatType;

    @Comment("이용 가능 좌석 수")
    @Column(name = "available_seat_count")
    private Integer availableSeatCount;

    @Builder
    public AirplaneSeatType (FlightEnums.SeatType seatType, Integer availableSeatCount) {
        this.seatType = seatType;
        this.availableSeatCount = availableSeatCount;
    }

    public void registerAirplane(Airplane airplane) {
        this.airplane = airplane;
        airplane.getAirplaneSeatTypes().add(this);
    }
}
