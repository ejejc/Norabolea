package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.Address;
import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import com.example.jpamaster.accommodations.domain.entity.PopularFacility;
import com.example.jpamaster.accommodations.enums.AccomodationsEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@ApiModel(value = "숙소")
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

    private List<AccommoFacilityInfoDto> popularFacilitySeqs;
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

    public Integer findSortMatchForSeq(Long facilitySeq) {
        return this.popularFacilitySeqs.stream()
                    .filter(vo -> vo.getFacilitySeq().equals(facilitySeq))
                    .map(AccommoFacilityInfoDto::getSort)
                    .findFirst()
                    .orElse(null);
    }
}
