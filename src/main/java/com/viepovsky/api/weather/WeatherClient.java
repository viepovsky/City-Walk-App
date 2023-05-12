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
        HttpEntity<String> request = buildRequestEntityHeaders();

        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getWeatherApiEndpoint() + "/current/" + longitude + ", " + latitude)
                .queryParam("tempunit", "C")
                .queryParam("windunit", "KMH")
                .build()
                .toUri();

        ResponseEntity<Forecast> response = restTemplate.exchange(url, HttpMethod.GET, request, Forecast.class);
        return Optional.ofNullable(response.getBody()).orElse(new Forecast());
    }

    Forecast fetchForecastWeather(String longitude, String latitude) {
        HttpEntity<String> request = buildRequestEntityHeaders();

        URI url = UriComponentsBuilder
                .fromHttpUrl(config.getWeatherApiEndpoint() + "/forecast/daily/" + longitude + ", " + latitude)
                .queryParam("tempunit", "C")
                .queryParam("windunit", "KMH")
                .queryParam("periods", "12")
                .queryParam("dataset", "full")
                .build()
                .toUri();

        ResponseEntity<Forecast> response = restTemplate.exchange(url, HttpMethod.GET, request, Forecast.class);
        return Optional.ofNullable(response.getBody()).orElse(new Forecast());
    }

    private HttpEntity<String> buildRequestEntityHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getWeatherApiKey());
        headers.set("X-RapidAPI-Host", config.getWeatherApiHost());
        return new HttpEntity<>(headers);
    }

}
