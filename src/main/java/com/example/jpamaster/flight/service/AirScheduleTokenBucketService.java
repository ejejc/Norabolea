package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.AirScheduleReservationBucket;
import org.springframework.stereotype.Service;

@Service
public class AirScheduleTokenBucketService {

    AirScheduleReservationBucket createDefaultFlightTicketTokenBucket(int totalAvailableSeatCount,
        double airlineCostMultipleRate) {
        return AirScheduleReservationBucket.createDefault(totalAvailableSeatCount, airlineCostMultipleRate);
    }
}
