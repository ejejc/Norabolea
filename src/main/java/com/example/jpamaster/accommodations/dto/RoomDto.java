package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.BorrowRoom;
import com.example.jpamaster.accommodations.domain.entity.Media;
import com.example.jpamaster.accommodations.domain.entity.Room;
import lombok.Setter;

import java.util.List;

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
        Room room = Room.builder()
                      .roomPrice(this.roomPrice)
                      .standardPerson(this.standardPerson)
                      .checkInTime(this.checkInTime)
                      .checkOutTime(this.checkOutTime)
                      .useYn(this.useYn)
                      // BorrowRoom과 Room의 연관관계 주인은 room이므로 이렇게만 저장해주면 외래키가 저장된다,
                      .borrowRoom(this.borrow.changeEntity())
                      .build();
        for (RoomMedia vo : mediaList) {
            room.add(vo.changeEntity());
        }
        return room;
    }
}
