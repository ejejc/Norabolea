package com.example.jpamaster.flight.feign;

import com.example.jpamaster.flight.initializing.FlightInitializer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "airlineFeignClient", url = "${open-api.airline.request-uri}")
public interface AirlineFeignClient {

    @GetMapping
    FlightInitializer.AirlineInfoVo fetchAirline (@RequestParam("serviceKey") String serviceKey, @RequestParam("type") String type);
}
