package com.example.jpamaster.flight.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static com.example.jpamaster.flight.constant.FlightConstant.EARTH_RADIUS;

public class FlightUtils {

    public static int getDistanceAsKm (double fromLat, double fromLon, double toLat, double toLon) {
        double dLat = Math.toRadians(toLat - fromLat);
        double dLon = Math.toRadians(toLon - fromLon);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(fromLat))* Math.cos(Math.toRadians(toLat))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (int) Math.ceil(EARTH_RADIUS * c );
    }

    public static LocalDateTime toLocalDateTime (String date, String time) {
        return LocalDateTime.of(getYear(date), getMonth(date), getDay(date), getHour(time), getMinute(time));
    }

    public static ZonedDateTime localDateTimeToZonedDateTimeWithUtc (LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneOffset.UTC);
    }
    public static LocalDateTime calculateArriveLocalDateTime (String zoneId, LocalDateTime localDateTime, int hour, int min) {

        ZonedDateTime zonedDateTime = localDateTimeToZonedDateTimeWithUtc(localDateTime)
                .plusHours(hour).plusMinutes(min);

        ZoneId zoneOffset = ZoneId.of(zoneId);
        return zonedDateTime
                .withZoneSameInstant(zoneOffset)
                .toLocalDateTime();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static int getYear(String date) {
        return Integer.parseInt(date.substring(0, 4));
    }

    private static int getMonth(String date) {
        return Integer.parseInt(date.substring(4, 6));
    }

    private static int getDay(String date) {
        return Integer.parseInt(date.substring(6));
    }

    private static int getHour(String time) {
        return Integer.parseInt(time.substring(0, 2));
    }

    private static int getMinute(String time) {
        return Integer.parseInt(time.substring(2));
    }
}
