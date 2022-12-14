package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.repository.AirlineRepository;
import com.example.jpamaster.flight.domain.repository.AirplaneRepository;
import com.example.jpamaster.flight.web.dto.req.AirplaneRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirplaneService {

    private final AirlineRepository airlineRepository;
    private final AirplaneRepository airplaneRepository;

    @Transactional
    public void registerAirplane(Long airlineSeq, AirplaneRegisterRequestDto dto) {
        airlineRepository.findById(airlineSeq)
                .ifPresent(airline -> {
                    Airplane airplane = Airplane.builder()
                            .code(dto.getCode())
                            .manufacturer(dto.getManufacturer())
                            .type(dto.getType())
                            .standardSeatCount(dto.getStandardSeatCount())
                            .maxSeatCount(dto.getMaxSeatCount())
                            .wifiUsable(dto.getWifiUsable())
                            .powerConsentUsable(dto.getPowerConsentUsable())
                            .usbUsable(dto.getUsbUsable())
                            .videoType(dto.getVideoType())
                            .foodSupplyType(dto.getFoodSupplyType())
                            .airline(airline)
                            .build();

                    airplaneRepository.save(airplane);
                });
    }
}
