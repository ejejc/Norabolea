package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.dto.FeaturesDto;
import com.example.jpamaster.accommodations.repository.FeaturesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FeaturesService {

    private final FeaturesRepository featuresRepository;

    public void add(FeaturesDto featuresDto) {
        featuresRepository.save(featuresDto.toEntity());
    }
}
