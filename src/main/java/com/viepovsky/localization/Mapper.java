package com.viepovsky.localization;

import com.viepovsky.localization.dto.CityResponse;
import com.viepovsky.localization.dto.CountryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class Mapper {
    CountryResponse mapToCountryResponse(Country country) {
        return new CountryResponse(
                country.getName(),
                country.getCode()
        );
    }

    List<CountryResponse> mapToCountryResponseList(List<Country> countries) {
        return countries.stream()
                .map(this::mapToCountryResponse)
                .toList();
    }

    CityResponse mapToCityResponse(City city) {
        return new CityResponse(
                city.getCountryCode(),
                city.getName(),
                city.getLatitude(),
                city.getLongitude()
        );
    }

    List<CityResponse> mapToCityResponseList(List<City> cities) {
        return cities.stream()
                .map(this::mapToCityResponse)
                .toList();
    }
}
