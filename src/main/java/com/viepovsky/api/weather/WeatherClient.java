package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.Forecast;
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
class WeatherClient {
    private final WeatherConfig config;
    private final RestTemplate restTemplate;

    Forecast fetchCurrentWeather(String longitude, String latitude) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getWeatherApiKey());
        headers.set("X-RapidAPI-Host", config.getWeatherApiHost());
        HttpEntity<String> request = new HttpEntity<>(headers);
        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getWeatherApiEndpoint() + "/current/" + longitude + ", " + latitude)
                .queryParam("tempunit", "C")
                .queryParam("windunit", "KMH")
                .build()
                .toUri();
        ResponseEntity<Forecast> response = restTemplate.exchange(url, HttpMethod.GET, request, Forecast.class);
        return Optional.ofNullable(response.getBody()).orElse(new Forecast());
    }
}
