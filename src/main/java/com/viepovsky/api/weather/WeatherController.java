package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.CurrentWeather;
import com.viepovsky.api.weather.dto.ForecastWeather;
import com.viepovsky.api.weather.dto.Wear;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/city-weather-app/weather")
@RequiredArgsConstructor
@Validated
class WeatherController {
    private final WeatherService service;

    @GetMapping
    ResponseEntity<CurrentWeather> getWeather(
            @RequestParam(name = "country-code") @NotBlank String code,
            @RequestParam(name = "city") @NotBlank String name
    ) {
        return ResponseEntity.ok(service.fetchCurrentWeather(code, name));
    }

    @GetMapping(path = "/forecast")
    ResponseEntity<List<ForecastWeather>> getForecast(
            @RequestParam(name = "country-code") @NotBlank String code,
            @RequestParam(name = "city") @NotBlank String name
    ) {
        return ResponseEntity.ok(service.fetchForecastWeather(code, name));
    }

    @GetMapping(path = "/wear")
    ResponseEntity<Wear> getWear(
            @RequestParam @FutureOrPresent @NotNull LocalDate date,
            @RequestParam(name = "country-code") @NotBlank String code,
            @RequestParam(name = "city") @NotBlank String name
    ) {
        if (LocalDate.now().plusDays(11).isBefore(date)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getComfortableWear(date, code, name));
    }
}

