package com.example.jpamaster.flight.domain.repository;

import com.example.jpamaster.common.annotations.ConfiguredDataJpaTest;
import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.AirplaneSeatType;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.entity.SeatType;
import com.example.jpamaster.flight.fixture.Fixture;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ConfiguredDataJpaTest
class AirplaneRepositoryTest {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private AirplaneRepository airplaneRepository;
    @Autowired
    private AirplaneSeatTypeRepository airplaneSeatTypeRepository;

    private Airport airport;
    private Airline airline;

    @BeforeEach
    void setup () {
        Airline airline = Fixture.generateAirline();
        Airport airport = Fixture.generateAirport();

        airport = airportRepository.save(airport);
        airline = airlineRepository.save(airline);
    }
    @DisplayName("비행기를 저장하면 비행기 좌석 타입도 같이 저장된다.")
    @Test
    void given_whenRegisterAirplane_thenRegisterAirplaneSeat() {
        // given
        Airplane airplane = generateAirplane();

        // when
        airplaneRepository.saveAndFlush(airplane);

        // then
        List<AirplaneSeatType> savedAirplaneSeatTypes = airplaneSeatTypeRepository.findAll();

        Assertions.assertThat(savedAirplaneSeatTypes.size())
            .isEqualTo(savedAirplaneSeatTypes.size());
        Assertions.assertThat(savedAirplaneSeatTypes)
            .element(0)
            .extracting(SeatType::getSeq)
            .isNotNull();
    }

    private Airplane generateAirplane() {
        Airplane airplane = Airplane.builder()
            .manufacturer(Fixture.AIRPLANE_MANUFACTURER)
            .code(Fixture.AIRPLANE_CODE)
            .type(Fixture.AIRPLANE_TYPE)
            .airline(airline)
            .currentAirport(airport)
            .build();

        Set<AirplaneSeatType> airplaneSeatTypes = Fixture.generateAirplaneSeatTypes();
        airplaneSeatTypes.forEach(airplaneSeatType -> airplaneSeatType.registerAirplane(airplane));
        return airplane;
    }

    @DisplayName("비행기를 삭제하면 비행기 좌석 타입도 같이 삭제된다.")
    @Test
    void given_whenDeleteAirplane_thenDeleteAirplaneSeat() {
        // given
        Airplane airplane = generateAirplane();
        Airplane savedAirplane = airplaneRepository.saveAndFlush(airplane);

        // when
        airplaneRepository.delete(savedAirplane);

        // then
        List<AirplaneSeatType> airplaneSeatTypeList = airplaneSeatTypeRepository.findAll();

        Assertions.assertThat(airplaneSeatTypeList).isEmpty();
    }
}