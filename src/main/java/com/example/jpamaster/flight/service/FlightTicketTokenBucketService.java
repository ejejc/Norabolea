package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.FlightTicketTokenBucket;
import org.springframework.stereotype.Service;

@Service
public class FlightTicketTokenBucketService {

    FlightTicketTokenBucket createDefaultFlightTicketTokenBucket(int totalAvailableSeatCount,
        double airlineCostMultiple) {
        return FlightTicketTokenBucket.createDefault(totalAvailableSeatCount, airlineCostMultiple);
    }
}
