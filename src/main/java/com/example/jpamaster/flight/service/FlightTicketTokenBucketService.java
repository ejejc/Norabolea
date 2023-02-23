package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.entity.FlightTicketTokenBucket;
import org.springframework.stereotype.Service;

@Service
public class FlightTicketTokenBucketService {

    FlightTicketTokenBucket createDefaultFlightTicketTokenBucket(Integer totalAvailableSeatCount){
        return FlightTicketTokenBucket.createDefault(totalAvailableSeatCount);
    }
}
