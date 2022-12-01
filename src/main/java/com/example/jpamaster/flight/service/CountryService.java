package com.example.jpamaster.flight.service;

import com.example.jpamaster.flight.domain.Country;
import com.example.jpamaster.flight.domain.CountryRepository;
import com.example.jpamaster.flight.web.dto.req.CountryInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CountryService {



    @Value("${open-api.country-code.request-uri}")
    private String requestUri;

    @Value("${open-api.country-code.api-key}")
    private String apiKey;

    private final CountryRepository countryRepository;


    @Transactional
    public void saveCountriesByPublicApi() {
        countryRepository.deleteAllInBatch();

        String uri = String.format("%s?" +
                "serviceKey=%s" +
                "&returnType=JSON" +
                "&numOfRows=1000" +
                "&pageNo=1", requestUri, apiKey);

        try {
            ResponseEntity<String> response = new RestTemplate().getForEntity(uri, String.class);
            if (response.hasBody()) {
                CountryInfo countryInfo = new ObjectMapper().readValue(response.getBody(), CountryInfo.class);

                List<Country> countries = new ArrayList<>();

                for (CountryInfo.Country country : countryInfo.getCountries()) {
                    Country newCountry = Country.toEntity(country);
                    countries.add(newCountry);
                }

                countryRepository.saveAll(countries);
            }
        } catch (JsonProcessingException e) {
            log.error("[COUNTRY] getCountryInfo >> json parsing error", e);
            // TODO: common error class
            throw new RuntimeException(e);
        }
    }
}
