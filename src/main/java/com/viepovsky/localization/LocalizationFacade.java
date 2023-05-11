package com.viepovsky.localization;

import com.viepovsky.localization.dto.CityResponse;
import com.viepovsky.localization.dto.CountryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class LocalizationFacade {
    private final CountryService countryService;
    private final CityService cityService;
    private final Mapper mapper;

    List<CountryResponse> getCountries() {
        List<Country> countries = countryService.getAll();
        return mapper.mapToCountryResponseList(countries);
    }

    List<CityResponse> getCities(String code) {
        List<City> cities = cityService.getByCode(code);
        return mapper.mapToCityResponseList(cities);
    }

    CityResponse getCity(String code, String name) {
        var city = cityService.getCity(code, name);
        return mapper.mapToCityResponse(city);
    }
}
