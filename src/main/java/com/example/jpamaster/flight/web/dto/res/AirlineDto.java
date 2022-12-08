package com.example.jpamaster.flight.web.dto.res;

import lombok.Getter;

@Getter
public class AirlineDto {
    private Long airlineSeq;
    private String krName;
    private String enName;

    public AirlineDto(Long airlineSeq, String krName, String enName) {
        this.airlineSeq = airlineSeq;
        this.krName = krName;
        this.enName = enName;
    }
}
