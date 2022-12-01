package com.example.jpamaster.flight.web.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class AirportDto {
    private String nameEn;

    private String nameKr;

    private String IATACode;

    private String ICAOCode;

    private String location;

    private String countryEn;

    private String countryKr;

    private String cityEn;
}
