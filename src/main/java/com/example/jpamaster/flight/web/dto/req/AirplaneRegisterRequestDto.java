package com.example.jpamaster.flight.web.dto.req;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class AirplaneRegisterRequestDto {
    private String manufacturer;
    private String code;
    private String type;
    private Set<SeatRegisterRequestDto> seatRegisterRequestDtos = new HashSet<>();
    private Long initialAirportSeq;
}
