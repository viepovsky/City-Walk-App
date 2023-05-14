package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.CurrentWeather;
import com.viepovsky.api.weather.dto.ForecastWeather;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city-weather-app/weather")
@RequiredArgsConstructor
@Validated
class WeatherController {
    private final WeatherService service;

    @GetMapping
    ResponseEntity<CurrentWeather> getWeather(
            @RequestParam(name = "latitude") @NotBlank String latitude,
            @RequestParam(name = "longitude") @NotBlank String longitude
    ) {
        return ResponseEntity.ok(service.fetchCurrentWeather(latitude, longitude));
    }

    @GetMapping(path = "/forecast")
    ResponseEntity<List<ForecastWeather>> getForecast(
            @RequestParam(name = "latitude") @NotBlank String latitude,
            @RequestParam(name = "longitude") @NotBlank String longitude
    ) {
        return ResponseEntity.ok(service.fetchForecastWeather(latitude, longitude));
    }
}

