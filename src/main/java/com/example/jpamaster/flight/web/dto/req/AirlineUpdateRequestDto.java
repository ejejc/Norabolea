package com.example.jpamaster.flight.web.dto.req;

import lombok.Getter;

@Getter
public class AirlineUpdateRequestDto {
    private String airlineName;
    private String airlineTel;
    private String airlineIcTel;

    public AirlineUpdateRequestDto(String airlineName, String airlineTel, String airlineIcTel) {
        this.airlineName = airlineName;
        this.airlineTel = airlineTel;
        this.airlineIcTel = airlineIcTel;
    }
}
