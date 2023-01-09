package com.example.jpamaster.flight.web.dto.req;

import com.example.jpamaster.flight.enums.FlightEnums;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = {"seatType"})
@Getter
public class AirplaneSeatRegisterRequestDto {
    private FlightEnums.SeatType seatType;
    private Integer availableSeatCount;
}
