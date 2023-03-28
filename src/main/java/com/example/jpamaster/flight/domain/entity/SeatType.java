package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import com.example.jpamaster.flight.enums.FlightEnums;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "seatType", callSuper = false)
@MappedSuperclass
public abstract class SeatType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Comment("좌석 종류")
    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type")
    private FlightEnums.SeatType seatType;

    @Comment("이용 가능 좌석 수")
    @Column(name = "available_seat_count")
    private Integer availableSeatCount;

    public SeatType(FlightEnums.SeatType seatType, Integer availableSeatCount) {
        this.seatType = seatType;
        this.availableSeatCount = availableSeatCount;
    }
}
