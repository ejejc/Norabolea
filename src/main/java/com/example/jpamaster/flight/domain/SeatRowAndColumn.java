package com.example.jpamaster.flight.domain;

import lombok.Getter;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class SeatRowAndColumn {

    @Comment("좌석 행")
    @Column(name = "seat_row")
    private Integer seatRow;

    @Comment("좌석 열")
    @Column(name = "seat_column", length = 1)
    private Character seatColumn;
}
