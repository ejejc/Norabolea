package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.BorrowRoom;
import com.example.jpamaster.accommodations.domain.entity.Media;
import com.example.jpamaster.accommodations.domain.entity.Room;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel(value = "룸")
public class RoomDto { //TODO: 어떤 클래스 구조가 좋을까?

    @ApiModelProperty(value = "방 가격")
    private Long roomPrice;
    @ApiModelProperty(value = "기준 인원")
    private int standardPerson;
    @ApiModelProperty(value = "최대 인원")
    private int maxPerson;
    @ApiModelProperty(value = "체크인 시간")
    private String checkInTime;
    @ApiModelProperty(value = "체크아웃 시간")
    private String checkOutTime;
    @ApiModelProperty(value = "사용 여부")
    private boolean useYn;
    @ApiModelProperty(value = "숙소 이미지 리스트")
    private List<RoomMedia> mediaList;
    @ApiModelProperty(value = "숙소 대실")
    private Borrow borrow;

    @Setter
    @Getter
    @ApiModel(value = "룸 미디어")
    private static class RoomMedia {
        @ApiModelProperty(value = "이미지 URL")
        private String mediaUrl;
        @ApiModelProperty(value = "대표 이미지 여부")
        private boolean mainFlag;
        @ApiModelProperty(value = "사용 여부")
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
    @Getter
    @ApiModel(value = "대실")
    private static class Borrow {
        @ApiModelProperty(value = "대실 시간")
        private int borrowTime;
        @ApiModelProperty(value = "시간당 대실 가격")
        private long borrowPrice;
        @ApiModelProperty(value = "대실 운영 시간")
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
                      .maxPerson(this.maxPerson)
                      .checkInTime(this.checkInTime)
                      .checkOutTime(this.checkOutTime)
                      .useYn(this.useYn)
                      // BorrowRoom과 Room의 연관관계 주인은 room이므로 이렇게만 저장해주면 외래키가 저장된다,
                      .borrowRoom(this.borrow.changeEntity())
                      .build();
        for (RoomMedia vo : mediaList) {
            room.addMedia(vo.changeEntity());
        }
        return room;
    }
}
