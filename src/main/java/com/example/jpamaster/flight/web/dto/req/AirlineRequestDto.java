package com.example.jpamaster.flight.web.dto.req;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AirlineRequestDto {
    private String airlineName;
    private String airlineTel;
    private String airlineIcTel;
}
