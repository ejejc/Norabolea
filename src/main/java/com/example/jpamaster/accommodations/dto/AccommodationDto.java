package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.Address;
import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import com.example.jpamaster.accommodations.enums.AccomodationsEnum;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class AccommodationDto {
    private String accommodationTitle;
    private String contact;
    private Address address;
    private AccomodationsEnum.Type accommodationsType;
    private List<RoomDto> rooms;

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
}
