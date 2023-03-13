package com.example.jpamaster.accommodation.repository;

import com.example.jpamaster.accommodations.domain.entity.AccommoFacilityInfo;
import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import com.example.jpamaster.accommodations.domain.entity.PopularFacility;
import com.example.jpamaster.accommodations.repository.AccommodationsRepository;
import com.example.jpamaster.accommodations.repository.AcommoFacilityInfoRepository;
import com.example.jpamaster.accommodations.repository.PopularFacilityRepository;
import com.example.jpamaster.common.annotations.ConfiguredDataJpaTest;
import com.example.jpamaster.popular_facility.repository.PopularFacilityFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ConfiguredDataJpaTest
public class AccommodationRepositoryTest {

    private PopularFacility popularFacility;
    private Accommodations accommodations;
    @Autowired
    AccommodationsRepository accommodationsRepository;
    @Autowired
    private PopularFacilityRepository popularFacilityRepository;
    @Autowired
    private AcommoFacilityInfoRepository acommoFacilityInfoRepository;

    @BeforeEach
    public void setUp() {
        popularFacility = PopularFacilityFixture.generatePopularFacility();
        popularFacilityRepository.save(popularFacility);

        accommodations = Fixture.generateAccommodation(List.of(popularFacility));
    }

    @Test
    public void 숙박등록_후_룸과_인기시설이_모두_저장되는지_확인한다() {
        // when
        accommodationsRepository.save(accommodations);

        // then
        Assertions.assertThat(accommodations.getAccommodationSeq()).isGreaterThan(0L);
        Assertions.assertThat(accommodations.getRooms().get(0).getRoomSeq()).isGreaterThan(0L);
        Assertions.assertThat(accommodations.getAccommoFacilityInfos().get(0).getSeq()).isGreaterThan(0L);
        AccommoFacilityInfo info = acommoFacilityInfoRepository.findById(accommodations.getAccommoFacilityInfos().get(0).getSeq()).orElse(null);
        Assertions.assertThat(info.getAccommodation()).isNotNull();
    }
}
