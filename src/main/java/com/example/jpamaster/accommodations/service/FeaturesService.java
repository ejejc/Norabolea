package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.Features;
import com.example.jpamaster.accommodations.domain.entity.RoomFeaturesInfo;
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

    public void add(FeaturesDto featuresDto) {
        featuresRepository.save(featuresDto.toEntity());
    }

    public List<Features> searchFeaturesListToByIds(List<FeaturesDto.FeaturesInfoDto> dtoList) {
        return featuresRepository.findAllById(
                dtoList.stream()
                        .map(FeaturesDto.FeaturesInfoDto::getFeatureSeq)
                        .collect(Collectors.toList())
        );
    }

    public FeaturesDto.FeaturesInfoDto searchSameFeatureSeqToEntitySeq(List<FeaturesDto.FeaturesInfoDto> dtoList, Long entityFeatureSeq) {
        return dtoList.stream()
                .filter(vo -> vo.getFeatureSeq().equals(entityFeatureSeq))
                .findFirst().orElseThrow(() -> new InvalidParameterException("유효하지 않은 룸입니다."));
    }
}
