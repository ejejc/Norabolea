package com.example.jpamaster.accommodations.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "인기시설 정보")
public class AccommoFacilityInfoDto {

    @Getter
    @Setter
    public static class Req {

        @ApiModelProperty(value = "인기시설 Seq")
        private Long facilitySeq;

        @ApiModelProperty(value = "인기시설 정렬 순서")
        private int sort;
    }

    @Getter
    @Builder
    public static class Res {

        @ApiModelProperty(value = "인기시설 이름")
        private String facilityName;

        @ApiModelProperty(value = "인기시설 정렬 순서")
        private int sort;

        @ApiModelProperty(value = "인기시설 로고 URL")
        private String logoUrl;
    }

}
