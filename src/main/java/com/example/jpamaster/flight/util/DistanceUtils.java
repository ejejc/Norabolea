package com.example.jpamaster.flight.util;

import static com.example.jpamaster.flight.constant.FlightConstant.EARTH_RADIUS;

public class DistanceUtils {

    public static int getDistanceAsKm (double fromLat, double fromLon, double toLat, double toLon) {
        double dLat = Math.toRadians(toLat - fromLat);
        double dLon = Math.toRadians(toLon - fromLon);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(fromLat))* Math.cos(Math.toRadians(toLat))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (int) Math.ceil(EARTH_RADIUS * c );
    }

}
