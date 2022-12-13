package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import com.example.jpamaster.flight.enums.FlightEnums;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "air_seat")
@Entity
public class AirSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airSeatSeq;


    @Comment("좌석 유형")
    @Column(name = "seat_type")
    @Enumerated(EnumType.STRING)
    private FlightEnums.SeatType seatType;


    @Embedded
    private SeatRowAndColumn seatRowAndColumn;

    @Comment("좌석 이용 가능 여부")
    @Column(name = "seat_usable")
    private Boolean seatUsable;

    @Comment(value = "항공기")
    @ManyToOne(fetch = FetchType.LAZY, cascade = ALL)
    @JoinColumn(name = "airplane_seq")
    private Airplane airplane;
}
