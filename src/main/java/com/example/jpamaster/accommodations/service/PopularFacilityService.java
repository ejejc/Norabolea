package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.PopularFacility;
import com.example.jpamaster.accommodations.repository.PopularFacilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PopularFacilityService {

    private final PopularFacilityRepository popularFacilityRepository;
    public void saveFacility(PopularFacility facility) {
        popularFacilityRepository.save(facility);
    }
}
