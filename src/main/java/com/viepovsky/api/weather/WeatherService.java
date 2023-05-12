package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.Forecast;
import com.viepovsky.api.weather.dto.Weather;
import com.viepovsky.localization.dto.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
class WeatherService {
    private final WeatherClient client;
    private final RestTemplate restTemplate;

    Weather fetchCurrentWeather(String code, String city) {
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/city-weather-app/localization/city")
                .queryParam("country-code", code)
                .queryParam("city", city)
                .encode()
                .build()
                .toUri();
        ResponseEntity<CityResponse> response = restTemplate.getForEntity(url, CityResponse.class);
        var cityResponse = response.getBody();
        Forecast forecast = client.fetchCurrentWeather(cityResponse.getLongitude(), cityResponse.getLatitude());
        return forecast.getCurrentWeather();
    }
}
