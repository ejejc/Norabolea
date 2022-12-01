package com.example.jpamaster.flight.domain;

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
public class AirSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airSeatSeq;

    @ManyToOne(fetch = FetchType.LAZY, cascade = ALL)
    @JoinColumn(name = "airplane_seq")
    @Comment(value = "항공기 시퀀스")
    private Airplane airplane;
}
