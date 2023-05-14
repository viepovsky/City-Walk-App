package com.viepovsky.api.airquality;

import com.viepovsky.api.airquality.dto.AirQuality;
import com.viepovsky.exceptions.AirQualityUnavailableException;
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
            @RequestParam(name = "latitude") @NotBlank String latitude,
            @RequestParam(name = "longitude") @NotBlank String longitude
    ) throws AirQualityUnavailableException {
        return ResponseEntity.ok(service.getAirQuality(latitude, longitude));
    }
}
