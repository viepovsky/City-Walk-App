package com.viepovsky.localization;

import com.viepovsky.localization.dto.CityResponse;
import com.viepovsky.localization.dto.CountryResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MapperTest {
    private final Mapper mapper = new Mapper();

    @Test
    void should_map_Country_to_CountryResponse() {
        var country = Country.builder().name("testname").code("testcode").build();

        var mappedCountry = mapper.mapToCountryResponse(country);

        assertThat(country).usingRecursiveComparison().isEqualTo(mappedCountry);
    }

    @Test
    void should_map_CountryList_to_CountryResponseList() {
        List<Country> countries = List.of(Country.builder().name("testname").code("testcode").build());

        List<CountryResponse> mappedCountries = mapper.mapToCountryResponseList(countries);

        assertThat(countries).usingRecursiveComparison().isEqualTo(mappedCountries);
    }

    @Test
    void should_map_City_to_CityResponse() {
        var city = City.builder().name("testname").countryCode("testcode").build();

        var mappedCity = mapper.mapToCityResponse(city);

        assertThat(city).usingRecursiveComparison().isEqualTo(mappedCity);
    }

    @Test
    void should_map_CityList_to_CityResponseList() {
        List<City> cities = List.of(City.builder().name("testname").countryCode("testcode").build());

        List<CityResponse> mappedCities = mapper.mapToCityResponseList(cities);

        assertThat(cities).usingRecursiveComparison().isEqualTo(mappedCities);
    }
}