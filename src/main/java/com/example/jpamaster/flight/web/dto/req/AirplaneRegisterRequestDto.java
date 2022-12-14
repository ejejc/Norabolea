package com.example.jpamaster.flight.web.dto.req;

import com.example.jpamaster.flight.enums.FlightEnums;
import lombok.Getter;

@Getter
public class AirplaneRegisterRequestDto {
    private String code;

    private String manufacturer;

    private String type;

    private Integer standardSeatCount = 0;

    private Integer maxSeatCount = 0;

    private Boolean wifiUsable = false;

    private Boolean powerConsentUsable = false;

    private Boolean usbUsable = false;

    private FlightEnums.VideoType videoType = FlightEnums.VideoType.NOT_SUPPORT;

    private FlightEnums.FoodSupplyType foodSupplyType = FlightEnums.FoodSupplyType.NOT_SUPPORT;
}
