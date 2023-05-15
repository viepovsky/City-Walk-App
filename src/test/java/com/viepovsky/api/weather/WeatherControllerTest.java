package com.viepovsky.api.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.api.weather.dto.CurrentWeather;
import com.viepovsky.api.weather.dto.ForecastWeather;
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
@WebMvcTest(WeatherController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService service;

    @Test
    void should_get_current_weather() throws Exception {
        var currentWeather = new CurrentWeather();
        var jsonResponse = new ObjectMapper().writeValueAsString(currentWeather);
        when(service.fetchCurrentWeather(anyString(), anyString())).thenReturn(currentWeather);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-walk-app/weather")
                        .param("latitude", "50.22")
                        .param("longitude", "5.22"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }

    @Test
    void should_get_forecast_weather() throws Exception {
        List<ForecastWeather> forecast = List.of(new ForecastWeather());
        var jsonResponse = new ObjectMapper().writeValueAsString(forecast);
        when(service.fetchForecastWeather(anyString(), anyString())).thenReturn(forecast);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-walk-app/weather/forecast")
                        .param("latitude", "50.22")
                        .param("longitude", "5.22"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }
}