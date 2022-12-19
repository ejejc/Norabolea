package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.domain.repository.AirplaneRepository;
import com.example.jpamaster.flight.domain.repository.AirportRepository;
import com.example.jpamaster.flight.web.dto.req.AirScheduleCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirScheduleCreateService {

    private final AirportRepository airportRepository;
    private final AirplaneRepository airplaneRepository;

    @Transactional
    public void createAirSchedule(AirScheduleCreateRequestDto dto) {


    }
}
