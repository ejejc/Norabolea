package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.PopularFacility;
import com.example.jpamaster.accommodations.dto.AccommoFacilityInfoDto;
import com.example.jpamaster.accommodations.repository.PopularFacilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PopularFacilityService {

    private final PopularFacilityRepository popularFacilityRepository;
    public void saveFacility(PopularFacility facility) {
        popularFacilityRepository.save(facility);
    }

    public List<PopularFacility> searchFacilityEntityToLongs (List<AccommoFacilityInfoDto.Req> dtoList) {
        return popularFacilityRepository.findAllById(
                dtoList.stream()
                        .map(AccommoFacilityInfoDto.Req::getFacilitySeq)
                        .collect(Collectors.toList())
        );
    }
}
