package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.Address;
import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import com.example.jpamaster.accommodations.enums.AccomodationsEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
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
    private List<AccommoFacilityInfoDto.Req> facilityInfoReq;

    private List<AccommoFacilityInfoDto.Res> facilityInfoRes;
    @ApiModelProperty(value = "평균 별점")
    private int avgStarScore;
    @ApiModelProperty(value = "리뷰 총 갯수")
    private int tatalReviewCnt;

    public Accommodations changeToEntity() {
        Accommodations accommodations = Accommodations.builder()
                .accommodationTitle(this.accommodationTitle)
                .contact(this.contact)
                .address(this.address)
                .accommodationsType(this.accommodationsType)
                .build();
        for (RoomDto dto : rooms) {
            accommodations.addRoom(dto.changeEntity());
        }
        return accommodations;
    }

    public static AccommodationDto changeToDto(Accommodations entity) {
        return AccommodationDto.builder()
                .accommodationTitle(entity.getAccommodationTitle())
                .contact(entity.getContact())
                .address(entity.getAddress())
                .accommodationsType(entity.getAccommodationsType())
                .rooms(entity.getRooms().stream().map(RoomDto::changeToDto).collect(Collectors.toList()))
                .facilityInfoRes(entity.getAccommoFacilityInfos().stream().map(AccommoFacilityInfoDto.Res::changeToDto).collect(Collectors.toList()))
                .build();
    }
    public Integer findSortMatchForSeq(Long facilitySeq) {
        return this.facilityInfoReq.stream()
                    .filter(vo -> vo.getFacilitySeq().equals(facilitySeq))
                    .map(AccommoFacilityInfoDto.Req::getSort)
                    .findFirst()
                    .orElse(null);
    }
}
