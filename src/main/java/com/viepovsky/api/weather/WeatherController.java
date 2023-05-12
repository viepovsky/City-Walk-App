package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.Weather;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city-weather-app/weather")
@RequiredArgsConstructor
class WeatherController {
    private final WeatherService service;

    @GetMapping
    ResponseEntity<Weather> getWeather(
            @RequestParam(name = "country-code") @NotBlank String code,
            @RequestParam(name = "city") @NotBlank String name
    ) {
        return ResponseEntity.ok(service.fetchCurrentWeather(code, name));
    }
}

