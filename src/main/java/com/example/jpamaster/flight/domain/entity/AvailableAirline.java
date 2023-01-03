package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class AvailableAirline extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long availableAirlineSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airport_seq")
    private Airport airport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_seq")
    private Airline airline;

    private String availableFrom;
    private String availableTo;

    @Builder
    public AvailableAirline (Airport airport, Airline airline, String availableFrom, String availableTo) {
        registerAvailableAirline(airport, airline);
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    public void registerAvailableAirline(Airport airport, Airline airline) {
        this.airport = airport;
        airport.getAvailableAirline().add(this);
        this.airline = airline;
        airline.getAvailableAirline().add(this);
    }
}
