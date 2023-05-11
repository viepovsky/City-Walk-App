package com.viepovsky.localization;

import com.viepovsky.localization.dto.CityResponse;
import com.viepovsky.localization.dto.CountryResponse;
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
@RequiredArgsConstructor
@RequestMapping(path = "/city-weather-app/localization")
@Validated
class LocalizationController {
    private final LocalizationFacade facade;

    @GetMapping("/countries")
    ResponseEntity<List<CountryResponse>> getCountries() {
        return ResponseEntity.ok(facade.getCountries());
    }

    @GetMapping("/cities")
    ResponseEntity<List<CityResponse>> getCities(@RequestParam(name = "country-code") @NotBlank String code) {
        return ResponseEntity.ok(facade.getCities(code));
    }

    @GetMapping("/city")
    ResponseEntity<CityResponse> getCity(
            @RequestParam(name = "country-code") @NotBlank String code,
            @RequestParam(name = "city") @NotBlank String name
    ) {
        return ResponseEntity.ok(facade.getCity(code, name));
    }
}
