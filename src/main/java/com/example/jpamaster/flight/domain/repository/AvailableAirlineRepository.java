package com.example.jpamaster.flight.domain.repository;

import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.AvailableAirline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailableAirlineRepository extends JpaRepository<AvailableAirline, Long> {
    long countByAirline_AirlineSeqAndAirport_AirportSeqIn(Airline airline, List<Long> airportSeqs);
}
