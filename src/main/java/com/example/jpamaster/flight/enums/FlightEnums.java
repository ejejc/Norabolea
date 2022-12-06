package com.example.jpamaster.flight.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FlightEnums {

    @Getter
    @RequiredArgsConstructor
    public enum SeatType {
        FIRST_CLASS("first-class", "퍼스트 클래스"),
        BUSINESS("business", "비지니스"),
        PREMIUM_ECONOMY("premium-economy", "프리미엄 이코노미"),
        ECONOMY("economy", "이코노미"),
        ;

        private final String enName;
        private final String krName;
    }

    @Getter
    @RequiredArgsConstructor
    public enum VideoType {
        WIDE_SCREEN("wide screen", "와이드 스크린"),
        SEAT_REAR_SCREEN("seat rear screen", "좌석 뒤 스크린"),
        PERSONAL_MONITOR_ON_DEMAND_VIDEO("personal monitor on-demand video", "개인 모니터 주문형 비디오"),
        PERSONAL_DEVICE("personal device", "개인 단말기"),
        ;
        private final String enName;
        private final String krName;
    }

    @Getter
    @RequiredArgsConstructor
    public enum FoodSupplyType {
        IN_FLIGHT_MEAL("in-flight meal", "기내식"),
        SNACK("snack", "스낵"),
        ;

        private final String enName;
        private final String krName;
    }
}
