package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.AccommoFacilityInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccommoFacilityInfoDto {

    @Getter
    @Setter
    public static class Req {
        private Long facilitySeq;
        private int sort;
    }

    @Getter
    @Builder
    public static class Res {
        private String facilityName;
        private int sort;
        private String logoUrl;

        public static AccommoFacilityInfoDto.Res changeToDto(AccommoFacilityInfo entity) {
            return Res.builder()
                    .facilityName(entity.getPopularFacility().getName())
                    .sort(entity.getSort())
                    .logoUrl(entity.getPopularFacility().getLogoUrl()).build();
        }
    }

}
