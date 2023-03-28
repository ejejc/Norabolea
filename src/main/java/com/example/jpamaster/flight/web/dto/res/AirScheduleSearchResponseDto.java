package com.example.jpamaster.flight.web.dto.res;

import com.example.jpamaster.flight.enums.FlightEnums;
import com.example.jpamaster.flight.enums.FlightEnums.AirlineType;
import com.example.jpamaster.flight.enums.FlightEnums.BucketTokenType;
import com.example.jpamaster.flight.enums.FlightEnums.DisplayType;
import com.example.jpamaster.flight.enums.FlightEnums.FoodType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AirScheduleSearchResponseDto {
    private Long airScheduleSeq;
    private LocalDateTime departAt;
    private LocalDateTime arriveAt;
    private Integer flightDistanceKm;
    private Integer estimatedHourSpent;
    private Integer estimatedMinuteSpent;

    private Long airScheduleSeatTypeSeq;
    private FlightEnums.SeatType seatType;
    private FoodType foodType;
    private Integer availableBaggageCount;
    private Integer availableBaggageWeight;
    private DisplayType displayType;
    private Boolean wifiAvailability;
    private Boolean usbAvailability;

    private String manufacturer;
    private String code;
    private String type;

    private String airlineImage;
    private String airlineName;
    private String airlineIata;

    private String deptNameEn;
    private String deptNameKr;
    private String deptIataCode;
    private String deptCountryEn;
    private String deptCountryKr;
    private String deptCityEn;


    private String arrvNameEn;
    private String arrvNameKr;
    private String arrvIataCode;
    private String arrvCountryEn;
    private String arrvCountryKr;
    private String arrvCityEn;

    @JsonIgnore
    private double reservationBucketCostMultipleRate;

    @JsonIgnore
    private AirlineType airlineType;

    private int priceForSchedule;

    public void setPriceForSchedule(int priceForSchedule) {
        this.priceForSchedule = priceForSchedule;
    }
}
