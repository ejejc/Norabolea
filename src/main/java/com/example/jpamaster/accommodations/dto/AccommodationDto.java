package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.Address;
import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import com.example.jpamaster.accommodations.enums.AccomodationsEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ApiModel(value = "숙소")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccommodationDto {

    @ApiModelProperty(value = "숙소 이름")
    private String accommodationTitle;

    @ApiModelProperty(value = "숙소 전화번호")
    private String contact;

    @ApiModelProperty(value = "숙소 주소")
    private Address address;

    @ApiModelProperty(value = "숙소 타입 - (EX. HOTEL, MOTEL, PENSION ...)")
    private AccomodationsEnum.Type accommodationsType;

    @ApiModelProperty(value = "방")
    private List<RoomDto> rooms;

    @ApiModelProperty(value = "인기시설 정보 - 요청 객체")
    private List<AccommoFacilityInfoDto.Req> facilityInfoReq;

    @ApiModelProperty(value = "인기시설 정보 - 응답 객체")
    private List<AccommoFacilityInfoDto.Res> facilityInfoRes;
    @ApiModelProperty(value = "평균 별점")
    private double avgStarScore = 0.0;
    @ApiModelProperty(value = "리뷰 총 갯수")
    private int totalReviewCnt = 0;

    public void calculateRevieScore(Double collect, int size) {
        totalReviewCnt = size;
        avgStarScore = collect / size;

    }
}
