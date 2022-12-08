package com.example.jpamaster.flight.web.dto.res;

import lombok.Getter;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;

@Getter
public class AirlineDto {
    private Long airlineSeq;
    private String airlineImage;
    private String airlineName;
    private String airlineTel;
    private String airlineIcTel;
    private String airlineIata;
    private String airlineIcao;

    public AirlineDto(Long airlineSeq, String airlineImage, String airlineName, String airlineTel, String airlineIcTel, String airlineIata, String airlineIcao) {
        this.airlineSeq = airlineSeq;
        this.airlineImage = airlineImage;
        this.airlineName = airlineName;
        this.airlineTel = airlineTel;
        this.airlineIcTel = airlineIcTel;
        this.airlineIata = airlineIata;
        this.airlineIcao = airlineIcao;
    }
}
