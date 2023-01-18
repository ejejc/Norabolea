package com.example.jpamaster.accommodations.dto;

import com.example.jpamaster.accommodations.domain.entity.Features;
import lombok.Getter;

@Getter
public class FeaturesDto {
    private String name;
    private String iconUrl;

    public Features toEntity() {
        return Features.builder()
                .featuresName(this.name)
                .featuresIconUrl(this.iconUrl).build();
    }

    @Getter
    public static class FeaturesInfoDto {
        private Long featureSeq;
        private int sort;
    }
}
