package com.viepovsky.api.airquality;

import com.viepovsky.api.airquality.dto.AirQuality;
import com.viepovsky.exceptions.AirQualityUnavailableException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class AirQualityClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirQualityClient.class);
    private final RestTemplate restTemplate;
    private final AirQualityConfig config;

    AirQuality fetchAirQuality(String latitude, String longitude) throws AirQualityUnavailableException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getAirQualityApiKey());
        headers.set("X-RapidAPI-Host", config.getAirQualityApiHost());
        HttpEntity<String> request = new HttpEntity<>(headers);

        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getAirQualityApiEndpoint())
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .build()
                .toUri();
        int maxTries = 10, tryCount = 0;
        while (tryCount < maxTries) {
            try {
                ResponseEntity<AirQuality> response = restTemplate.exchange(url, HttpMethod.GET, request, AirQuality.class);
                return Optional.ofNullable(response.getBody()).orElse(new AirQuality());
            } catch (HttpStatusCodeException e) {
                if (e.getStatusCode() == HttpStatus.BAD_GATEWAY) {
                    LOGGER.warn("API: " + config.getAirQualityApiEndpoint() + " not responding.");
                    if (++tryCount == maxTries) {
                        throw new AirQualityUnavailableException("API: " + config.getAirQualityApiEndpoint() + " is not available.");
                    }
                } else {
                    throw e;
                }
            }
        }
        return new AirQuality();
    }
}
