package com.viepovsky.api.airquality;

import com.viepovsky.api.airquality.dto.AirQuality;
import com.viepovsky.exceptions.WrongArgumentException;
import com.viepovsky.localization.dto.CityResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
class AirQualityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirQualityService.class);
    private final AirQualityClient client;
    private final RestTemplate restTemplate;

    AirQuality getAirQuality(String code, String city) {
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/city-weather-app/localization/city")
                .queryParam("country-code", code)
                .queryParam("city", city)
                .encode()
                .build()
                .toUri();
        try {
            ResponseEntity<CityResponse> response = restTemplate.getForEntity(url, CityResponse.class);
            CityResponse cityResponse = response.getBody();
            return client.fetchAirQuality(cityResponse.getLatitude(), cityResponse.getLongitude());
        } catch (HttpClientErrorException e) {
            LOGGER.error(e.getMessage());
            throw new WrongArgumentException(e.getMessage().substring(4));
        }
    }
}
