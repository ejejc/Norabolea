package com.example.jpamaster.accommodation.repository;

import com.example.jpamaster.accommodations.domain.entity.*;
import com.example.jpamaster.accommodations.enums.AccomodationsEnum;
import java.util.ArrayList;
import java.util.List;

public class Fixture {

    public static Accommodations generateAccommodation(List<PopularFacility> popularFacilityList) {
        List<AccommoFacilityInfo> accommoFacilityInfos = new ArrayList<>();

        Accommodations accommodations = Accommodations.builder()
                .accommodationTitle("세형이 숙소")
                .contact("01022349811")
                .accommodationsType(AccomodationsEnum.Type.HOTEL).build();
        for (int i=0; i<popularFacilityList.size(); i++) {
            accommoFacilityInfos.add(
                    AccommoFacilityInfo.builder()
                    .accommodation(accommodations)
                    .popularFacility(popularFacilityList.get(i))
                    .sort(i).build()
            );
        }
        accommodations.setAccommoFacilityInfos(accommoFacilityInfos);
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
        /*Features features = Features.builder()
                .featuresName("수영장")
                .featuresIconUrl("/media/swim").build();
        RoomFeaturesInfo info = RoomFeaturesInfo.builder()
                .features(features)
                .sort(1).build();
        room.setRoomFeaturesInfoList(List.of(info));*/

        accommodations.setRooms(List.of(room));
        return accommodations;
    }
}
