package com.example.jpamaster.popular_facility.repository;

import com.example.jpamaster.accommodations.domain.entity.PopularFacility;
import com.example.jpamaster.accommodations.repository.PopularFacilityRepository;
import com.example.jpamaster.common.annotations.ConfiguredDataJpaTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@ConfiguredDataJpaTest
class PopularFacilityRepositoryTest {

    @Autowired
    private PopularFacilityRepository popularFacilityRepository;

    private PopularFacility pcPark;

    @BeforeEach
    void setUp() {
        pcPark = PopularFacilityFixture.generatePopularFacility();
    }

    @Test
    @DisplayName("인기시설 저장 테스트")
    void saveFaclityTest() {
        // when
        PopularFacility pcParkEntity = popularFacilityRepository.save(pcPark);

        // then
        Assertions.assertThat(pcParkEntity.getPopularFacilitySeq()).isGreaterThan(0L);
    }

}