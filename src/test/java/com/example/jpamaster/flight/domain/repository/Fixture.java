package com.example.jpamaster.flight.domain.repository;

import com.example.jpamaster.flight.constant.FlightConstant;
import com.example.jpamaster.flight.domain.entity.Airline;
import com.example.jpamaster.flight.domain.entity.Airplane;
import com.example.jpamaster.flight.domain.entity.AirplaneSeatType;
import com.example.jpamaster.flight.domain.entity.Airport;
import com.example.jpamaster.flight.enums.FlightEnums;
import com.example.jpamaster.flight.enums.FlightEnums.SeatType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Fixture {

    private static final String[] airportCsvFixture = {
        "Incheon International Airport,인천 국제공항,ICN,RKSI,아시아,Asia/Seoul,Republic of Korea,대한민국,Incheon,37.46910095,126.4509964",
        "Daegu International Airport,대구 국제공항,TAE,RKTN,아시아,Asia/Seoul,Republic of Korea,대한민국,Daegu,35.896872,128.65531",
        "Gwangju Airport,광주 공항,KWJ,RKJJ,아시아,Asia/Seoul,Republic of Korea,대한민국,Gwangju,35.123173,126.805444",
        "Gunsan Airport,군산 공항,KUV,RKJK,아시아,Asia/Seoul,Republic of Korea,대한민국,Gunsan,35.90380096,126.6159973",
        "Wonju Airport,원주 공항,WJU,RKNW,아시아,Asia/Seoul,Republic of Korea,대한민국,Wonju,37.441201,127.963858",
        "Jeju International Airport,제주 국제공항,CJU,RKPC,아시아,Asia/Seoul,Republic of Korea,대한민국,Jeju,33.51129913,126.4930038",
    };
    public static final String AIRPLANE_MANUFACTURER = "boeing";
    public static final String AIRPLANE_CODE = "777X";
    public static final String AIRPLANE_TYPE = "777-8";

    public static List<Airport> generateAllAirportByFixture() {

        return Arrays.stream(airportCsvFixture)
            .map(airport -> airport.split(","))
            .map(airport -> createAirport(airport)
            )
            .collect(Collectors.toUnmodifiableList());
    }

    private static Airport createAirport(String[] airport) {
        return Airport.airportGenerator()
            .nameEn(airport[FlightConstant.ENG_AIRPORT_NAME_IDX])
            .nameKr(airport[FlightConstant.KOR_AIRPORT_NAME_IDX])
            .IATACode(airport[FlightConstant.IATA_CODE_IDX])
            .ICAOCode(airport[FlightConstant.ICAO_CODE_IDX])
            .locationKr(airport[FlightConstant.KOR_AIRPORT_LOCATION_IDX])
            .locationEn(airport[FlightConstant.ENG_AIRPORT_LOCATION_IDX])
            .countryEn(airport[FlightConstant.ENG_COUNTRY_IDX])
            .countryKr(airport[FlightConstant.KOR_COUNTRY_IDX])
            .cityEn(airport[FlightConstant.ENG_CITY_IDX])
            .lat(airport[FlightConstant.LATITUDE_IDX])
            .lon(airport[FlightConstant.LONGITUDE_IDX])
            .generate();
    }

    public static Airport generateAirport() {
        String[] airport = airportCsvFixture[airportCsvFixture.length - 1].split(",");
        return createAirport(airport);
    }
    public static Airline generateAirline() {
        return Airline.builder()
            .airlineImage("https://odp.airport.kr/apiPortal/airlineIconDown?IATA_CODE=KE")
            .airlineName("대한항공")
            .airlineTel("1588-2001")
            .airlineIcTel("1588-2001")
            .airlineIata("KE")
            .airlineIcao("KAL")
            .build();
    }

    public static List<AirplaneSeatType> generateAirplaneSeats() {
        return List.of(
            AirplaneSeatType.builder()
                .seatType(SeatType.FIRST_CLASS)
                .availableSeatCount(100)
                .build(),
            AirplaneSeatType.builder()
                .seatType(SeatType.BUSINESS)
                .availableSeatCount(100)
                .build(),
            AirplaneSeatType.builder()
                .seatType(SeatType.PREMIUM_ECONOMY)
                .availableSeatCount(100)
                .build(),
            AirplaneSeatType.builder()
                .seatType(SeatType.ECONOMY)
                .availableSeatCount(100)
                .build()
        );
    }
}
