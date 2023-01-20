package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.Features;
import com.example.jpamaster.accommodations.dto.FeaturesDto;
import com.example.jpamaster.accommodations.repository.FeaturesRepository;
import com.example.jpamaster.common.exception.InvalidParameterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FeaturesService {

    private final FeaturesRepository featuresRepository;

    public void add(FeaturesDto.Feature featureDto) {
        featuresRepository.save(featureDto.toEntity());
    }

    public List<Features> searchFeaturesListToByIds(List<FeaturesDto.FeatureInfoDto> dtoList) {
        return featuresRepository.findAllById(
                dtoList.stream()
                        .map(FeaturesDto.FeatureInfoDto::getFeatureSeq)
                        .collect(Collectors.toList())
        );
    }

    public FeaturesDto.FeatureInfoDto searchSameFeatureSeqToEntitySeq(List<FeaturesDto.FeatureInfoDto> dtoList, Long entityFeatureSeq) {
        return dtoList.stream()
                .filter(vo -> vo.getFeatureSeq().equals(entityFeatureSeq))
                .findFirst().orElseThrow(() -> new InvalidParameterException("유효하지 않은 룸입니다."));
    }
}
