package com.example.jpamaster.accommodation.service;

import com.example.jpamaster.accommodations.repository.AccommodationsRepository;
import com.example.jpamaster.accommodations.repository.review.ReviewRepository;
import com.example.jpamaster.accommodations.service.AccommodationService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccommodationServiceTest {

    @InjectMocks
    private AccommodationService accommodationService;
    @Mock
    private AccommodationsRepository accommodationsRepository;
    @Mock
    private ReviewRepository repository;


}
