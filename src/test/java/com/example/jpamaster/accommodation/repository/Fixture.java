package com.example.jpamaster.accommodation.repository;

import com.example.jpamaster.accommodations.domain.entity.*;
import com.example.jpamaster.accommodations.enums.AccomodationsEnum;
import java.util.ArrayList;
import java.util.List;

public class Fixture {

    public static Accommodations generateAccommodation(List<PopularFacility> popularFacilityList) {
        Accommodations accommodations = Accommodations.builder()
                .accommodationTitle("세형이 숙소")
                .contact("01022349811")
                .accommodationsType(AccomodationsEnum.Type.HOTEL).build();
        // TODO: 이게 맞는걸까 ?
        int sort = 0;
        List<AccommoFacilityInfo> facilityInfos = new ArrayList<>();
        for (PopularFacility pc : popularFacilityList) {
          facilityInfos.add( AccommoFacilityInfo.builder()
                  .popularFacility(pc)
                  .sort(sort).build());
          sort ++;
        }
        accommodations.setAccommoFacilityInfos(facilityInfos);

        Room room = Room.builder()
                .roomName("룸1")
                .roomPrice(1000L)
                .standardPerson(2)
                .maxPerson(2)
                .checkInTime("14:00")
                .checkOutTime("11:00")
                .useYn(true)
                .borrowRoom(BorrowRoom.builder()
                        .borrowTime(5)
                        .borrowPrice(1000)
                        .operateTime("3").build()).build();
        Media media = Media.builder()
                .mediaUrl("/test/url")
                .mainFlag(true)
                .useYn(true).build();
        room.setMedia(List.of(media));

        accommodations.setRooms(List.of(room));
        return accommodations;
    }
}
