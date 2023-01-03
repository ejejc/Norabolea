package com.example.jpamaster.flight.web.dto.req;

import com.example.jpamaster.flight.enums.FlightEnums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AirplaneRegisterRequestDto {
    private String manufacturer;
    private String code;
    private String type;
    private List<SeatRegisterRequestDto> seatRegisterRequestDtos = new ArrayList<>();
    private Long initialAirportSeq;
}
