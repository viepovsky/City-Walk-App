package com.viepovsky.recommendation;

import com.viepovsky.api.airquality.dto.AirQuality;
import com.viepovsky.api.weather.dto.CurrentWeather;
import com.viepovsky.api.weather.dto.ForecastWeather;
import com.viepovsky.exceptions.WrongArgumentException;
import com.viepovsky.recommendation.dto.Walk;
import com.viepovsky.recommendation.dto.Wear;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationService.class);
    private final RestTemplate restTemplate;

    public Walk getWalkRecommendation(String latitude, String longitude) {
        AirQuality airQuality = null;
        URI url = buildUri("airquality", latitude, longitude);
        try {
            ResponseEntity<AirQuality> response = restTemplate.getForEntity(url, AirQuality.class);
            airQuality = response.getBody();
        } catch (HttpClientErrorException e) {
            LOGGER.error(e.getMessage());
        }

        CurrentWeather weather;
        url = buildUri("weather", latitude, longitude);
        try {
            ResponseEntity<CurrentWeather> response = restTemplate.getForEntity(url, CurrentWeather.class);
            weather = response.getBody();
        } catch (HttpClientErrorException e) {
            LOGGER.error(e.getMessage());
            throw new WrongArgumentException(e.getMessage().substring(4));
        }

        if (Optional.ofNullable(airQuality).isEmpty() || Optional.ofNullable(weather).isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE);
        }

        Walk.AirQualityIndexScale aqiScale = retrieveAirQualityIndexScale(airQuality.getOverallAqi());
        Walk.UvIndexScale uvIndexScale = retrieveUvIndexScale(weather.getUvIndex());
        return new Walk(aqiScale, uvIndexScale);
    }

    private URI buildUri(String endpoint, String latitude, String longitude) {
        return UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/city-weather-app/" + endpoint)
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .encode()
                .build()
                .toUri();
    }

    private Walk.AirQualityIndexScale retrieveAirQualityIndexScale(String overallAqi) {
        Walk.AirQualityIndexScale airQualityIndexScale;
        int aqi = Integer.parseInt(overallAqi);
        if (aqi <= 50) {
            airQualityIndexScale = Walk.AirQualityIndexScale.GOOD;
        } else if (aqi <= 100) {
            airQualityIndexScale = Walk.AirQualityIndexScale.MODERATE;
        } else if (aqi <= 150) {
            airQualityIndexScale = Walk.AirQualityIndexScale.UNHEALTHY_FOR_SENSITIVE_GROUPS;
        } else if (aqi <= 200) {
            airQualityIndexScale = Walk.AirQualityIndexScale.UNHEALTHY;
        } else if (aqi <= 300) {
            airQualityIndexScale = Walk.AirQualityIndexScale.VERY_UNHEALTHY;
        } else {
            airQualityIndexScale = Walk.AirQualityIndexScale.HAZARDOUS;
        }
        return airQualityIndexScale;
    }

    private Walk.UvIndexScale retrieveUvIndexScale(int uvIndex) {
        Walk.UvIndexScale scale;
        switch (uvIndex) {
            case 0, 1, 2 -> scale = Walk.UvIndexScale.LOW;
            case 3, 4, 5 -> scale = Walk.UvIndexScale.MODERATE;
            case 6, 7 -> scale = Walk.UvIndexScale.HIGH;
            case 8, 9, 10 -> scale = Walk.UvIndexScale.VERY_HIGH;
            default -> scale = Walk.UvIndexScale.EXTREME;
        }
        return scale;
    }

    public Wear getWearRecommendation(LocalDate date, String latitude, String longitude) {
        List<ForecastWeather> forecast;
        URI url = buildUri("weather/forecast", latitude, longitude);
        try {
            ForecastWeather[] forecasts = restTemplate.getForObject(url, ForecastWeather[].class);
            forecast = Arrays.asList(forecasts);
        } catch (HttpClientErrorException exception) {
            LOGGER.error(exception.getMessage());
            throw exception;
        }
        ForecastWeather weather = getForecastWeatherForGivenDate(forecast, date);
        Wear comfortableWear = calculateComfortableWear(weather);
        changeWearIfRains(weather, comfortableWear);
        return comfortableWear;
    }

    private ForecastWeather getForecastWeatherForGivenDate(List<ForecastWeather> forecast, LocalDate date) {
        return forecast.stream()
                .filter(w -> w.getDate().isEqual(date))
                .findFirst()
                .orElse(new ForecastWeather());
    }

    private Wear calculateComfortableWear(ForecastWeather weather) {
        int feelsLikeTemp = (int) Math.round(weather.getMaxTemp() * 0.75 + weather.getMinTemp() * 0.25);
        Wear comfortableWear;
        if (feelsLikeTemp >= 40) {
            comfortableWear = new Wear(Wear.TemperatureScale.SCORCHING_HOT);
        } else if (feelsLikeTemp >= 30) {
            comfortableWear = new Wear(Wear.TemperatureScale.HOT);
        } else if (feelsLikeTemp >= 23) {
            comfortableWear = new Wear(Wear.TemperatureScale.WARM);
        } else if (feelsLikeTemp >= 17) {
            comfortableWear = new Wear(Wear.TemperatureScale.MODERATE);
        } else if (feelsLikeTemp >= 13) {
            comfortableWear = new Wear(Wear.TemperatureScale.COOL);
        } else if (feelsLikeTemp >= 8) {
            comfortableWear = new Wear(Wear.TemperatureScale.CHILLY);
        } else if (feelsLikeTemp >= 1) {
            comfortableWear = new Wear(Wear.TemperatureScale.COLD);
        } else {
            comfortableWear = new Wear(Wear.TemperatureScale.FREEZING);
        }
        return comfortableWear;
    }

    private void changeWearIfRains(ForecastWeather weather, Wear comfortableWear) {
        if (weather.getPrecipationAccumulated() >= 0.5 && weather.getPrecipationPropability() > 60) {
            comfortableWear.setRainAndRainClothes();
        } else if (weather.getPrecipationAccumulated() >= 0.1 && weather.getPrecipationPropability() > 20) {
            comfortableWear.setPossibleRain();
        }
    }
}
