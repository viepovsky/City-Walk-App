package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.CurrentWeather;
import com.viepovsky.api.weather.dto.Forecast;
import com.viepovsky.api.weather.dto.ForecastWeather;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WeatherServiceTest {
    @InjectMocks
    private WeatherService service;

    @Mock
    private WeatherClient client;

    @Test
    void should_fetch_forecast_current_weather() {
        var weather = new CurrentWeather();
        var forecast = new Forecast();
        forecast.setCurrentWeather(weather);
        when(client.fetchCurrentWeather(anyString(), anyString())).thenReturn(forecast);

        var retrievedWeather = service.fetchCurrentWeather("50", "20");

        assertNotNull(retrievedWeather);
        verify(client, times(1)).fetchCurrentWeather(anyString(), anyString());
    }

    @Test
    void should_fetch_forecast_weather() {
        var forecastWeathers = List.of(new ForecastWeather());
        var forecast = new Forecast();
        forecast.setForecastWeathers(forecastWeathers);
        when(client.fetchForecastWeather(anyString(), anyString())).thenReturn(forecast);

        var retrievedForecastWeathers = service.fetchForecastWeather("50", "20");

        assertNotNull(retrievedForecastWeathers);
        assertEquals(1, retrievedForecastWeathers.size());
        verify(client, times(1)).fetchForecastWeather(anyString(), anyString());
    }
}