package com.viepovsky.localization;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LocalizationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @MockBean
    private CityService cityService;

    @Test
    void should_get_countries() throws Exception {
        List<Country> countries = List.of(new Country());
        when(countryService.getAll()).thenReturn(countries);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-weather-app/localization/countries"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void should_get_cities_for_given_country_code() throws Exception {
        List<City> cities = List.of(new City());
        when(cityService.getByCode(anyString())).thenReturn(cities);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-weather-app/localization/cities")
                        .param("country-code", "PL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void should_get_city_for_given_country_code_and_city() throws Exception {
        City city = new City();
        when(cityService.getCity(anyString(), anyString())).thenReturn(city);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-weather-app/localization/city")
                        .param("country-code", "PL")
                        .param("city", "Poznań"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()));
    }
}