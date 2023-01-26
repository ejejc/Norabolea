package com.example.jpamaster.flight.initializing;

import static org.junit.jupiter.api.Assertions.*;

import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class FlightInitializerTest {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AirportRepository airportRepository;

    @DisplayName("스프링 컨텍스트가 구동된 후 ApplicationReadyEvent 발생으로 initialize 를 확인한다.")
    @Test
    public void applicationReadyEventTest() {
        // when
        long airlineCount = airlineRepository.count();
        long airportCount = airportRepository.count();

        // then
        Assertions.assertThat(airlineCount).isGreaterThan(0);
        Assertions.assertThat(airportCount).isGreaterThan(0);
    }
}