package com.example.jpamaster.accommodations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class KakaoDto {


    @Getter
    @Setter
    public static class Res {
        private String addressName;
        private AddressType addressType;
        private String x;
        private String y;
    }

    @Getter
    @AllArgsConstructor
    public static enum AddressType {
        REGION("지명"),
        ROAD("도로명"),
        REGION_ADDR("지번 주소"),
        ROAD_ADDR("도로명 주소");

        private String desc;
    }


}
