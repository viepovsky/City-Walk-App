package com.viepovsky.localization;

import com.viepovsky.localization.dto.CityResponse;
import com.viepovsky.localization.dto.CountryResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LocalizationFacadeTest {
    @InjectMocks
    private LocalizationFacade facade;

    @Mock
    private CountryService countryService;

    @Mock
    private CityService cityService;

    @Mock
    private Mapper mapper;

    @Test
    void should_get_countries() {
        List<Country> countries = List.of(new Country());
        List<CountryResponse> countryResponses = List.of(new CountryResponse());
        when(countryService.getAll()).thenReturn(countries);
        when(mapper.mapToCountryResponseList(anyList())).thenReturn(countryResponses);

        List<CountryResponse> retrievedResponses = facade.getCountries();

        assertNotNull(retrievedResponses);
        assertEquals(1, retrievedResponses.size());
    }

    @Test
    void should_get_cities_for_given_country_code() {
        List<City> city = List.of(new City());
        List<CityResponse> cityResponses = List.of(new CityResponse());
        when(cityService.getByCode(anyString())).thenReturn(city);
        when(mapper.mapToCityResponseList(anyList())).thenReturn(cityResponses);

        List<CityResponse> retrievedResponses = facade.getCities("PL");

        assertNotNull(retrievedResponses);
        assertEquals(1, retrievedResponses.size());
    }

    @Test
    void should_get_city_for_given_country_code_and_city() {
        var city = new City();
        var cityResponse = new CityResponse();
        when(cityService.getCity(anyString(), anyString())).thenReturn(city);
        when(mapper.mapToCityResponse(any(City.class))).thenReturn(cityResponse);

        var retrievedResponse = facade.getCity("PL", "Poznań");

        assertNotNull(retrievedResponse);
    }
}