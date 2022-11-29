package com.example.jpamaster.accommodations.service;

import com.example.jpamaster.accommodations.domain.entity.Accommodations;
import com.example.jpamaster.accommodations.dto.AccommodationDto;
import com.example.jpamaster.accommodations.repository.AccommodationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccommodationService {
    private final AccommodationsRepository accommodationsRepository;
    public void addAccommodation(Accommodations entity) {
        accommodationsRepository.save(entity);
    }
}
