package com.example.jpamaster.flight.web.dto.req;

import com.example.jpamaster.flight.enums.FlightEnums;
import com.example.jpamaster.flight.enums.FlightEnums.DisplayType;
import com.example.jpamaster.flight.enums.FlightEnums.FoodType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "seatType")
@Getter
public class AirScheduleSeatRequestDto {
    private FlightEnums.SeatType seatType;
    private Integer availableSeatCount = 0;
    private FoodType foodType = FoodType.NOT_SUPPORT;
    private Integer availableBaggageCount = 0;
    private Integer availableBaggageWeight = 0;
    private DisplayType displayType = DisplayType.NOT_SUPPORT;
    private Boolean wifiAvailability = false;
    private Boolean usbAvailability = false;
    private Integer availableChildCount = 0;
}
