package com.example.jpamaster.flight.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.AirplaneSeatType;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.domain.repository.AirplaneRepository;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import com.example.jpamaster.flight.fixture.Fixture;
import com.example.jpamaster.flight.web.dto.req.AirplaneRegisterRequestDto;
import com.example.jpamaster.flight.web.dto.req.AirplaneSeatRegisterRequestDto;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AirplaneServiceTest {

    @InjectMocks
    private AirplaneService airplaneService;

    @Mock
    private AirlineRepository airlineRepository;

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private AirplaneRepository airplaneRepository;

    @Mock
    private SeatService seatService;

    private Airline airline;
    private Airport airport;
    private Set<AirplaneSeatType> airplaneSeatTypes;

    @BeforeEach
    public void setup() {
        airline = Fixture.generateAirline();
        airport = Fixture.generateAirport();
        airplaneSeatTypes = Fixture.generateAirplaneSeatTypes();
    }

    @DisplayName("비행기 등록 테스트")
    @Test
    void givenAirplaneRegisterRequestDto_whenSaveAirplane_thenSaveAirplane() {
        // given
        AirplaneRegisterRequestDto dto = new AirplaneRegisterRequestDto(
            "manufacturer",
            "code",
            "type",
            Collections.emptySet(),
            1L
        );

        BDDMockito.given(airlineRepository.findById(ArgumentMatchers.any()))
            .willReturn(Optional.of(airline));
        BDDMockito.given(airportRepository.findByAirportSeq(ArgumentMatchers.any()))
            .willReturn(Optional.of(airport));
        BDDMockito.given(seatService.createAirplaneSeatType(ArgumentMatchers.any()))
            .willReturn(airplaneSeatTypes);

        // when
        airplaneService.registerAirplane(1L, dto);

        // then
        BDDMockito.verify(airplaneRepository, Mockito.times(1))
            .save(ArgumentMatchers.any());
    }

}