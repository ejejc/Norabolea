package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.Features;
import com.example.jpamaster.accommodations.domain.entity.RoomFeaturesInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class FeaturesDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Feature { // TODO: 이부분 오빠랑 코드 리뷰 및 해결 방법 블로그에 정리
        private String name;
        private String iconUrl;

        public Features toEntity() {
            return Features.builder()
                    .featuresName(this.name)
                    .featuresIconUrl(this.iconUrl).build();
        }

        public static List<Feature> makeFeaturesDto(List<RoomFeaturesInfo> roomFeaturesInfoList) {
           return roomFeaturesInfoList.stream()
                    .map(vo -> new FeaturesDto.Feature(vo.getFeatures().getFeaturesName()
                            , vo.getFeatures().getFeaturesIconUrl()))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class FeatureInfoDto {
        private Long featureSeq;
        private int sort;
        private Feature features;
    }
}
