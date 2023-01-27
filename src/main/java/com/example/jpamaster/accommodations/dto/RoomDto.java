package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "룸")
public class RoomDto { //TODO: 어떤 클래스 구조가 좋을까?

    private Long seq;
    @ApiModelProperty(value = "방 가격")
    private Long roomPrice;
    @ApiModelProperty(value = "방 이름")
    private String roomName;
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
    @ApiModelProperty(value = "특징 리스트 seq - REQ")
    private List<FeaturesDto.FeatureInfoDto> featureList;
    @ApiModelProperty(value = "특징 리스트 seq - RES")
    private List<FeaturesDto.Feature> featuresDtos;
    @ApiModelProperty(value = "숙소 대실")
    private Borrow borrow;

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "룸 미디어")
    public static class RoomMedia {
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

        public static RoomMedia changeToDto(Media entity) {
            return RoomMedia.builder()
                    .mediaUrl(entity.getMediaUrl())
                    .mainFlag(entity.isMainFlag())
                    .useYn(entity.isUseYn()).build();
        }
    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "대실")
    public static class Borrow {
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

        public static RoomDto.Borrow changeToDto(BorrowRoom entity) {
            return Borrow.builder()
                    .borrowTime(entity.getBorrowTime())
                    .borrowPrice(entity.getBorrowPrice())
                    .operateTime(entity.getOperateTime()).build();
        }
    }

    public Room changeEntity() {
        Room room = Room.builder()
                      .roomPrice(this.roomPrice)
                      .roomName(this.roomName)
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

    public static RoomDto changeToDto(Room entity) {
        RoomDto roomDto
         = RoomDto.builder()
                .seq(entity.getRoomSeq())
                .roomPrice(entity.getRoomPrice())
                .roomName(entity.getRoomName())
                .standardPerson(entity.getStandardPerson())
                .maxPerson(entity.getMaxPerson())
                .checkInTime(entity.getCheckInTime())
                .checkOutTime(entity.getCheckOutTime())
                .useYn(entity.isUseYn())
                .featuresDtos(entity.getRoomFeaturesInfoList().stream().map(FeaturesDto.FeatureInfoDto::of).collect(Collectors.toList()))
                .mediaList(entity.getMedia().stream().map(RoomMedia::changeToDto).collect(Collectors.toList()))
                //.featuresDtos()
                .borrow(RoomDto.Borrow.changeToDto(entity.getBorrowRoom())).build();
        return roomDto;
    }
}
