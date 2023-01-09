package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.AirSchedule;
import com.example.jpamaster.flight.domain.entity.AirScheduleSeatType;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.AirplaneSeatType;
import com.example.jpamaster.flight.web.dto.req.AirScheduleSeatRegisterRequestDto;
import com.example.jpamaster.flight.web.dto.req.AirplaneSeatRegisterRequestDto;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SeatService {

    @Transactional
    public void registerSeatForAirplane(Set<AirplaneSeatRegisterRequestDto> dtos, Airplane airplane) {
        if (!dtos.isEmpty()) {
            for (AirplaneSeatRegisterRequestDto seatDto : dtos) {
                AirplaneSeatType airplaneSeatType = AirplaneSeatType.builder()
                    .seatType(seatDto.getSeatType())
                    .availableSeatCount(seatDto.getAvailableSeatCount())
                    .build();

                airplaneSeatType.registerAirplane(airplane);
            }
        }
    }

    @Transactional
    public void registerSeatForAirSchedule(Set<AirScheduleSeatRegisterRequestDto> dtos, AirSchedule airSchedule) {
        if (!dtos.isEmpty()) {
            for (AirScheduleSeatRegisterRequestDto dto : dtos) {
                if (dto.getSeatType() == null) {
                    continue;
                }

                AirScheduleSeatType airScheduleSeatType = AirScheduleSeatType.builder()
                    .seatType(dto.getSeatType())
                    .availableSeatCount(dto.getAvailableSeatCount())
                    .foodType(dto.getFoodType())
                    .availableBaggageCount(dto.getAvailableBaggageCount())
                    .availableBaggageWeight(dto.getAvailableBaggageWeight())
                    .displayType(dto.getDisplayType())
                    .wifiAvailability(dto.getWifiAvailability())
                    .usbAvailability(dto.getUsbAvailability())
                    .build();

                airScheduleSeatType.registerAirSchedule(airSchedule);
            }
        }


    }
}
