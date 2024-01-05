package com.viepovsky.recommendation;

import com.viepovsky.api.airquality.dto.AirQuality;
import com.viepovsky.api.weather.dto.CurrentWeather;
import com.viepovsky.api.weather.dto.ForecastWeather;
import com.viepovsky.recommendation.dto.Walk;
import com.viepovsky.recommendation.dto.Wear;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecommendationServiceTest {
    @InjectMocks
    private RecommendationService service;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RecommendationConfig config;

    @Test
    void should_get_walk_recommendation() {
        var airQuality = new AirQuality();
        airQuality.setOverallAqi("50");
        var airQualityResponseEntity = new ResponseEntity<>(airQuality, HttpStatus.OK);
        when(restTemplate.getForEntity(any(), eq(AirQuality.class))).thenReturn(airQualityResponseEntity);

        var weather = new CurrentWeather();
        weather.setUvIndex(2);
        var weatherResponseEntity = new ResponseEntity<>(weather, HttpStatus.OK);
        when(restTemplate.getForEntity(any(), eq(CurrentWeather.class))).thenReturn(weatherResponseEntity);
        when(config.getServerUrl()).thenReturn("http://localhost:8080");

        var walkRecommendation = service.getWalkRecommendation("50", "20");

        assertNotNull(walkRecommendation);
        assertEquals(Walk.Recommendation.RECOMMENDED, walkRecommendation.getRecommendation());
    }

    @Test
    void should_get_wear_recommendation() {
        var date = LocalDate.now();
        var forecasts = new ForecastWeather[1];
        forecasts[0] = new ForecastWeather();
        forecasts[0].setDate(date);
        forecasts[0].setMinTemp(17);
        forecasts[0].setMaxTemp(23);
        forecasts[0].setPrecipationAccumulated(0.01);
        forecasts[0].setPrecipationPropability(10);
        when(restTemplate.getForObject(any(), eq(ForecastWeather[].class))).thenReturn(forecasts);
        when(config.getServerUrl()).thenReturn("http://localhost:8080");

        var wearRecommendation = service.getWearRecommendation(date, "50", "20");

        assertNotNull(wearRecommendation);
        assertEquals(Wear.WeatherDescription.MODERATE, wearRecommendation.getWeatherDesc());
    }
}