package com.example.jpamaster.popular_facility.service;

import com.example.jpamaster.accommodations.repository.PopularFacilityRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PopularFacilityServiceTest {
    @Mock
    private PopularFacilityRepository popularFacilityRepository;
}
