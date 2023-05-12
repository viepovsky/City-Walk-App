package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.Forecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WeatherClientTest {
    @InjectMocks
    private WeatherClient client;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherConfig config;

    @BeforeEach
    void before_each() {
        when(config.getWeatherApiEndpoint()).thenReturn("https://test.com");
        when(config.getWeatherApiKey()).thenReturn("testkey");
        when(config.getWeatherApiHost()).thenReturn("testhost");
    }

    @Test
    void should_fetch_current_weather() throws URISyntaxException {
        //Given
        var forecast = new Forecast();
        HttpEntity<String> requestEntityHeaders = buildRequestEntityHeaders();

        ResponseEntity<Forecast> response = new ResponseEntity<>(forecast, HttpStatus.OK);

        URI url = new URI("https://test.com/current/50.4433,%2040.4455?tempunit=C&windunit=KMH");
        when(restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, Forecast.class)).thenReturn(response);
        //When
        var retrievedForecast = client.fetchCurrentWeather("50.4433", "40.4455");
        //Then
        assertNotNull(retrievedForecast);
        verify(restTemplate, times(1)).exchange(url, HttpMethod.GET, requestEntityHeaders, Forecast.class);
    }

    @Test
    void should_fetch_forecast_weather() throws URISyntaxException {
        //Given
        var forecast = new Forecast();
        HttpEntity<String> requestEntityHeaders = buildRequestEntityHeaders();

        ResponseEntity<Forecast> response = new ResponseEntity<>(forecast, HttpStatus.OK);

        URI url = new URI("https://test.com/forecast/daily/50.4433,%2040.4455?tempunit=C&windunit=KMH&periods=12&dataset=full");
        when(restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, Forecast.class)).thenReturn(response);
        //When
        var retrievedForecast = client.fetchForecastWeather("50.4433", "40.4455");
        //Then
        assertNotNull(retrievedForecast);
        verify(restTemplate, times(1)).exchange(url, HttpMethod.GET, requestEntityHeaders, Forecast.class);
    }

    HttpEntity<String> buildRequestEntityHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getWeatherApiKey());
        headers.set("X-RapidAPI-Host", config.getWeatherApiHost());
        return new HttpEntity<>(headers);
    }
}