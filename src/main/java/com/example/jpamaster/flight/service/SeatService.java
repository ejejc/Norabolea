package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.AirScheduleSeatType;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.AirplaneSeatType;
import com.example.jpamaster.flight.web.dto.req.AirScheduleSeatRequestDto;
import com.example.jpamaster.flight.web.dto.req.AirplaneSeatRegisterRequestDto;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SeatService {

    Set<AirplaneSeatType> createAirplaneSeatType(Set<AirplaneSeatRegisterRequestDto> dtos) {
        Set<AirplaneSeatType> airplaneSeatTypes = new HashSet<>();
        if (!dtos.isEmpty()) {
            for (AirplaneSeatRegisterRequestDto seatDto : dtos) {
                AirplaneSeatType airplaneSeatType = AirplaneSeatType.builder()
                    .seatType(seatDto.getSeatType())
                    .availableSeatCount(seatDto.getAvailableSeatCount())
                    .build();

                airplaneSeatTypes.add(airplaneSeatType);
            }
        }
        return airplaneSeatTypes;
    }

    Set<AirScheduleSeatType> createAirScheduleSeatType(Set<AirScheduleSeatRequestDto> dtos) {
        Set<AirScheduleSeatType> airScheduleSeatTypes = new HashSet<>();
        if (!dtos.isEmpty()) {
            for (AirScheduleSeatRequestDto dto : dtos) {
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
                    .availableChildSeatCount(dto.getAvailableChildCount())
                    .build();

                airScheduleSeatTypes.add(airScheduleSeatType);
            }
        }
        return airScheduleSeatTypes;

    }
}
