package com.viepovsky.api.airquality;

import com.viepovsky.api.airquality.dto.AirQuality;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class AirQualityClient {
    private final RestTemplate restTemplate;
    private final AirQualityConfig config;

    AirQuality fetchAirQuality(String latitude, String longitude) {
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

        ResponseEntity<AirQuality> response = restTemplate.exchange(url, HttpMethod.GET, request, AirQuality.class);
        return Optional.ofNullable(response.getBody()).orElse(new AirQuality());
    }
}
