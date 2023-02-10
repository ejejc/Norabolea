package com.example.jpamaster.flight.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FlightUtilsTest {

    private double fromLat = 37.4692;
    private double fromLon = 126.451;

    private double toLat = 34.4272222;
    private double toLon = 135.244167;

    private double toLat2 = 49.1966913;
    private double toLon2 = -123.1815123;

    @Test
    void dateTimeTest() {
        LocalDateTime expectedTime = FlightUtils.toLocalDateTime("20230114", "1130");
        LocalDateTime now = ZonedDateTime.now(ZoneId.of("America/Vancouver")).toLocalDateTime();

        Assertions.assertThat(expectedTime).isBefore(now);
    }

    @Test
    void haversiveFomulaDistanceAsKm() {
        int distanceAsKmToJapan = FlightUtils.getDistanceAsKm(fromLat, fromLon, toLat, toLon);
        int distanceAsKmToCanada = FlightUtils.getDistanceAsKm(fromLat, fromLon, toLat2, toLon2);
        double haversiveFomulaDistanceAsKmToJapan = FlightUtils.haversiveFomulaDistanceAsKm(fromLat, fromLon, toLat, toLon);
        double haversiveFomulaDistanceAsKmToCanada = FlightUtils.haversiveFomulaDistanceAsKm(fromLat, fromLon, toLat2, toLon2);

        Assertions.assertThat(distanceAsKmToJapan).isEqualTo((int) Math.ceil(haversiveFomulaDistanceAsKmToJapan));
        Assertions.assertThat(distanceAsKmToCanada).isEqualTo((int) Math.ceil(haversiveFomulaDistanceAsKmToCanada));
    }
}