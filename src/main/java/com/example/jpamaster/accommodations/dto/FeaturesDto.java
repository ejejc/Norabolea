package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.Features;
import com.example.jpamaster.accommodations.domain.entity.RoomFeaturesInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class FeaturesDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Feature {
        private String name;
        private String iconUrl;

        public Features toEntity() {
            return Features.builder()
                    .featuresName(this.name)
                    .featuresIconUrl(this.iconUrl).build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfoDto extends Feature {
        private Long featureSeq;
        private int sort;

        public FeatureInfoDto(String name, String iconUrl, int sort) {
            super(name, iconUrl);
            this.sort = sort;
        }

        public static FeatureInfoDto of (RoomFeaturesInfo entity) {
            return new FeatureInfoDto(entity.getFeatures().getFeaturesName()
                            , entity.getFeatures().getFeaturesIconUrl()
                            , entity.getSort());

        }
    }
}
