package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.PopularFacility;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

@ApiModel(value = "인기 시설")
public class FacilittyDto {

    @ApiModelProperty(value = "인기시설 이름")
    private String name;

    @ApiModelProperty(value = "시설 로고 이미지 URL")
    private String logoUrl;

    @ApiModelProperty(value = "등록 일자")
    private LocalDateTime regDate;

    @ApiModelProperty(value = "수정 일")
    private LocalDateTime updateDate;

    public PopularFacility toEntity() {
        return PopularFacility.builder()
                    .name(this.name)
                    .logoUrl(this.logoUrl).build();
    }
}
