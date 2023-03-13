package com.example.jpamaster.accommodations.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

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
    private List<FeaturesDto.FeatureInfoDto> roomFeaturesInfoList;

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
    }
}
