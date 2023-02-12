package com.example.jpamaster.flight.web.dto.req;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class AirplaneRegisterRequestDto {
    private String manufacturer;
    private String code;
    private String type;
    private Set<AirplaneSeatRegisterRequestDto> airplaneSeatRegisterRequestDtos = new HashSet<>();
    private Long initialAirportSeq;

    public AirplaneRegisterRequestDto(String manufacturer, String code, String type,
        Set<AirplaneSeatRegisterRequestDto> airplaneSeatRegisterRequestDtos, Long initialAirportSeq) {
        this.manufacturer = manufacturer;
        this.code = code;
        this.type = type;
        this.airplaneSeatRegisterRequestDtos = airplaneSeatRegisterRequestDtos;
        this.initialAirportSeq = initialAirportSeq;
    }
}
