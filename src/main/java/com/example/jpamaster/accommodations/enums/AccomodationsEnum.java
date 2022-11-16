package com.example.jpamaster.accommodations.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AccomodationsEnum {

    @Getter
    @AllArgsConstructor
    public enum Type {
        HOTEL("hotel", "호텔"),
        PENSION("pension", "펜션"),
        MOTEL("motel", "모텔"),
        GUEST_HOUSE("guest house", "게스트 하우스"),
        LODGE("lodge", "민박"),
        POOL_VILLA("pool villa", "풀 빌라"),
        AIR_BNB("airbnb", "에어비엔비"),
        ETC ("etc", "기타")
        ;

        private final String enValue;
        private final String koValue;
    }

    @Getter
    @AllArgsConstructor
    public enum SellerStatus {
        NORMAL("일반")
        , BLACK("블랙")
        , HUMAN("휴먼");

        private String desc;
    }
}
