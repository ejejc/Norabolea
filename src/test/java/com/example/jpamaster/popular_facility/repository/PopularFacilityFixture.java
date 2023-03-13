package com.example.jpamaster.popular_facility.repository;

import com.example.jpamaster.accommodations.domain.entity.PopularFacility;

public class PopularFacilityFixture {

    public static PopularFacility generatePopularFacility() {
        return PopularFacility.builder()
                .name("주차가능")
                .logoUrl("https://yaimg.yanolja.com/v5/2022/07/04/16/62c31409836037.64820304.png").build();
    }
}
