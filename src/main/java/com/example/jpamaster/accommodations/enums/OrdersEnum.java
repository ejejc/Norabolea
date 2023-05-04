package com.example.jpamaster.accommodations.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class OrdersEnum {

    @AllArgsConstructor
    @NoArgsConstructor
    public static enum CouponDiscountType {
        RATE("비율")
        , FIX("고정");

        private String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static enum Status {
        PAYMENT_COMPLETE(01, "결제 완료");

        private int code;
        private String desc;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static enum PointType {
        EARNED("지급")
        , USED("사용")
        , EXPIRED("만료");

        private String desc;
    }
}
