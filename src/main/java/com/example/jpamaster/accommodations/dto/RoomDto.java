package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.BorrowRoom;
import com.example.jpamaster.accommodations.domain.entity.Media;
import com.example.jpamaster.accommodations.domain.entity.Room;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
public class RoomDto { //TODO: 어떤 클래스 구조가 좋을까?

    private Long roomPrice;
    private int standardPerson;
    private int maxPerson;
    private String checkInTime;
    private String checkOutTime;
    private boolean useYn;
    private List<RoomMedia> mediaList;
    private Borrow borrow;

    @Setter
    private static class RoomMedia {
        private String mediaUrl;
        private boolean mainFlag;
        private boolean useYn;

        public Media changeEntity() {
            return Media.builder()
                        .mediaUrl(this.mediaUrl)
                        .mainFlag(this.mainFlag)
                        .useYn(this.useYn)
                        .build();
        }
    }

    @Setter
    private static class Borrow {
        private int borrowTime;
        private long borrowPrice;
        private String operateTime;

        public BorrowRoom changeEntity() {
            return BorrowRoom.builder()
                        .borrowTime(this.borrowTime)
                        .borrowPrice(this.borrowPrice)
                        .operateTime(this.operateTime)
                        .build();
        }
    }

    public Room changeEntity() {
        return Room.builder()
                .roomPrice(this.roomPrice)
                .standardPerson(this.standardPerson)
                .checkInTime(this.checkInTime)
                .checkOutTime(this.checkOutTime)
                .useYn(this.useYn)
                .borrowRoom(this.borrow.changeEntity())
                .media(this.mediaList.stream()
                        .map(RoomMedia::changeEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
