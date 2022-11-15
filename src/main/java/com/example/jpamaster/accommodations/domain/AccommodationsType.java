package com.example.jpamaster.accommodations.domain;

import lombok.Getter;

@Getter
public enum AccommodationsType {

    HOTEL("hotel", "호텔"),
    PENSION("pension", "펜션"),
    MOTEL("motel", "모텔"),
    GUEST_HOUSE("guest house", "게스트 하우스"),
    LODGE("lodge", "민박"),
    POOL_VILLA("pool villa", "풀 빌라"),
    AIR_BNB("airbnb", "에어비엔비"),
    ETC ("etc", "기타")
    ;

    AccommodationsType(String enValue, String koValue) {
        this.enValue = enValue;
        this.koValue = koValue;
    }

    private String enValue;
    private String koValue;
}
