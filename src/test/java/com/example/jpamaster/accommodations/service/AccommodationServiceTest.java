package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.Features;
import com.example.jpamaster.accommodations.domain.entity.PopularFacility;
import com.example.jpamaster.accommodations.repository.FeaturesRepository;
import com.example.jpamaster.accommodations.repository.PopularFacilityRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccommodationServiceTest {

    @Autowired
    private PopularFacilityRepository popularFacilityRepository;

    @Autowired
    private FeaturesRepository featuresRepository;

    @Test
    @DisplayName("인기시설 등록 테스트")
    void saveFaclityTest() {

        // given
        PopularFacility pcPark = PopularFacility.builder()
                .name("주차가능")
                .logoUrl("https://yaimg.yanolja.com/v5/2022/07/04/16/62c31409836037.64820304.png").build();
        PopularFacility pcPool = PopularFacility.builder()
                .name("야외수영장")
                .logoUrl("https://yaimg.yanolja.com/v5/2022/07/04/16/62c311543917c0.43365861.png")
                .build();
        PopularFacility pcSea = PopularFacility.builder()
                .name("바다전망")
                .logoUrl("https://yaimg.yanolja.com/v5/2022/07/04/16/62c314553461d5.03006332.png")
                .build();

        // when
        PopularFacility pcParkEntity = popularFacilityRepository.save(pcPark);
        PopularFacility pcPoolEntity = popularFacilityRepository.save(pcPool);
        PopularFacility pcSeaEntity = popularFacilityRepository.save(pcSea);

        // then
        assertEquals(pcPark.getName(), pcParkEntity.getName());
        assertEquals(pcPool.getName(), pcPoolEntity.getName());
        assertEquals(pcSea.getName(), pcSeaEntity.getName());
    }

    @Test
    @DisplayName("숙소특징 등록 테스트")
    void saveFeaturesTest() {

        // given
        Features featuresPerson = Features.builder()
                .featuresName("2인 / 최대 2인")
                .featuresIconUrl("https://yaimg.yanolja.com/stay/static/images/pic-theme-member.svg")
                .build();
        Features featuresNoSmoke = Features.builder()
                .featuresName("금연 객실")
                .featuresIconUrl("https://yaimg.yanolja.com/stay/static/images/pic-theme-member.svg")
                .build();

        // when
        Features fpEntity = featuresRepository.save(featuresPerson);
        Features fnsEntity = featuresRepository.save(featuresNoSmoke);

        // then
        assertEquals(featuresPerson.getFeaturesName(), fpEntity.getFeaturesName());
        assertEquals(featuresNoSmoke.getFeaturesName(), fnsEntity.getFeaturesName());
    }



}