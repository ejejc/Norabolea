package com.example.jpamaster.flight.domain.repository;

import com.example.jpamaster.common.annotations.ConfiguredDataJpaTest;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.fixture.Fixture;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;


@ConfiguredDataJpaTest
class AirportRepositoryTest {
    @Autowired
    private AirportRepository airportRepository;

    @BeforeEach
    void setup() {
        List<Airport> airportList = Fixture.generateAllAirportByFixture();
        airportRepository.saveAll(airportList);
    }

    @DisplayName("검색 조건에 맞는 결과 값 상위 3개의 공항만 조회한다.")
    @Test
    void given_whenFindTop3BySearchCondition_thenTop3AirportListByCondition() {
        // when
        List<Airport> top3BySearchCondition = airportRepository.findTop3BySearchCondition(
            "공항",
            PageRequest.of(0, 3)
        );

        // then
        Assertions.assertThat(top3BySearchCondition.size()).isEqualTo(3);
    }

}