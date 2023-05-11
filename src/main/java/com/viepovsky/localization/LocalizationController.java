package com.viepovsky.localization;

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
    private final CountryService countryService;
    private final CityService cityService;

    @GetMapping("/countries")
    ResponseEntity<List<Country>> getCountries() {
        return ResponseEntity.ok(countryService.getAll());
    }

    @GetMapping("/cities")
    ResponseEntity<List<City>> getCities(@RequestParam(name = "country-code") @NotBlank String code) {
        return ResponseEntity.ok(cityService.getByCode(code));
    }

    @GetMapping("/city")
    ResponseEntity<City> getCity(
            @RequestParam(name = "country-code") @NotBlank String code,
            @RequestParam(name = "city") @NotBlank String city
    ) {
        return ResponseEntity.ok(cityService.getCity(code, city));
    }
}
