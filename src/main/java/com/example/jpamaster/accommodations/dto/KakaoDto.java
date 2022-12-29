package com.example.jpamaster.accommodations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

public class KakaoDto {


    @Getter
    @Builder
    public static class Res {
        private String addressName;
        private AddressType addressType;
        private String x;
        private String y;

        public static Res KakaoResOfDto(Map<String, String> documentMap) {
            return  KakaoDto.Res.builder()
                    .addressName(documentMap.get("address_name"))
                    .addressType(AddressType.changeToAddressType(documentMap.get("address_type")))
                    .x(documentMap.get("x"))
                    .y(documentMap.get("y")).build();
        }

    }

    @Getter
    @AllArgsConstructor
    public static enum AddressType {
        REGION("지명"),
        ROAD("도로명"),
        REGION_ADDR("지번 주소"),
        ROAD_ADDR("도로명 주소");

        private String desc;

        public static AddressType changeToAddressType (String type) {
            return Arrays.stream(AddressType.values())
                    .filter(vo -> vo.toString().equals(type))
                    .findFirst()
                    .orElse(null);
        }
    }


}
