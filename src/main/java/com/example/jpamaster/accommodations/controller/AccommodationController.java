package com.example.jpamaster.accommodations.controller;

import com.example.jpamaster.accommodations.dto.AccommodationDto;
import com.example.jpamaster.accommodations.service.AccommodationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/accommodation")
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping
    public void add(@RequestBody AccommodationDto param) {
        accommodationService.addAccommodation(param.changeToEntity());
    }
}
