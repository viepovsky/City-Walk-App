package com.viepovsky.api.airquality;

import com.viepovsky.api.airquality.dto.AirQuality;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/city-weather-app/airquality")
@RequiredArgsConstructor
class AirQualityController {
    private final AirQualityService service;

    @GetMapping
    ResponseEntity<AirQuality> getAirQuality(
            @RequestParam(name = "country-code") @NotBlank String code,
            @RequestParam(name = "city") @NotBlank String name
    ) {
        return ResponseEntity.ok(service.getAirQuality(code, name));
    }
}
