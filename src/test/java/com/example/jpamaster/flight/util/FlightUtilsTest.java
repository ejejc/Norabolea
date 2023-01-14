package com.example.jpamaster.flight.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FlightUtilsTest {

    @Test
    void dateTimeTest() {
        LocalDateTime expectedTime = FlightUtils.toLocalDateTime("20230114", "1130");
        LocalDateTime now = ZonedDateTime.now(ZoneId.of("America/Vancouver")).toLocalDateTime();

        Assertions.assertThat(expectedTime).isAfter(now);
    }

}