package com.viepovsky.localization;

import com.viepovsky.localization.dto.CityResponse;
import com.viepovsky.localization.dto.CountryResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(LocalizationController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LocalizationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocalizationFacade facade;

    @Test
    void should_get_countries() throws Exception {
        List<CountryResponse> countryResponses = List.of(new CountryResponse());
        when(facade.getCountries()).thenReturn(countryResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-walk-app/localization/countries"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void should_get_cities_for_given_country_code() throws Exception {
        List<CityResponse> cityResponses = List.of(new CityResponse());
        when(facade.getCities(anyString())).thenReturn(cityResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-walk-app/localization/cities")
                        .param("country-code", "PL"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void should_get_city_for_given_country_code_and_city() throws Exception {
        var city = new CityResponse();
        when(facade.getCity(anyString(), anyString())).thenReturn(city);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-walk-app/localization/city")
                        .param("country-code", "PL")
                        .param("city", "Poznań"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()));
    }
}