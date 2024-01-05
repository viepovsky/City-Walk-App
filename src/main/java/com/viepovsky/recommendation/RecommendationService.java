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
    private final RecommendationConfig config;

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
                .fromHttpUrl(config.getServerUrl() + "/city-walk-app/" + endpoint)
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .encode()
                .build()
                .toUri();
    }

    private Walk.AirQualityIndexScale retrieveAirQualityIndexScale(String overallAqi) {
        Walk.AirQualityIndexScale airQualityIndexScale = null;
        int aqi = Integer.parseInt(overallAqi);
        List<Integer> thresholdValues = List.of(50, 100, 150, 200, 300);
        List<Walk.AirQualityIndexScale> scales = List.of(
                Walk.AirQualityIndexScale.GOOD,
                Walk.AirQualityIndexScale.MODERATE,
                Walk.AirQualityIndexScale.UNHEALTHY_FOR_SENSITIVE_GROUPS,
                Walk.AirQualityIndexScale.UNHEALTHY,
                Walk.AirQualityIndexScale.VERY_UNHEALTHY
        );
        for (int i = 0; i < thresholdValues.size(); i++) {
            if (aqi <= thresholdValues.get(i)) {
                airQualityIndexScale = scales.get(i);
                break;
            }
        }
        return Optional.ofNullable(airQualityIndexScale).orElse(Walk.AirQualityIndexScale.HAZARDOUS);
    }

    private Walk.UvIndexScale retrieveUvIndexScale(int uvIndex) {
        return switch (uvIndex) {
            case 0, 1, 2 -> Walk.UvIndexScale.LOW;
            case 3, 4, 5 -> Walk.UvIndexScale.MODERATE;
            case 6, 7 -> Walk.UvIndexScale.HIGH;
            case 8, 9, 10 -> Walk.UvIndexScale.VERY_HIGH;
            default -> Walk.UvIndexScale.EXTREME;
        };
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
        Wear comfortableWear = null;
        List<Integer> thresholdValues = List.of(40, 30, 23, 17, 13, 8, 1);
        List<Wear.WeatherDescription> scales = List.of(
                Wear.WeatherDescription.SCORCHING_HOT,
                Wear.WeatherDescription.HOT,
                Wear.WeatherDescription.WARM,
                Wear.WeatherDescription.MODERATE,
                Wear.WeatherDescription.COOL,
                Wear.WeatherDescription.CHILLY,
                Wear.WeatherDescription.COLD
        );
        for (int i = 0; i < thresholdValues.size(); i++) {
            if (feelsLikeTemp >= thresholdValues.get(i)) {
                comfortableWear = new Wear(scales.get(i));
                break;
            }
        }
        return Optional.ofNullable(comfortableWear).orElse(new Wear(Wear.WeatherDescription.FREEZING));
    }

    private void changeWearIfRains(ForecastWeather weather, Wear comfortableWear) {
        if (weather.getPrecipationAccumulated() >= 0.5 && weather.getPrecipationPropability() > 60) {
            comfortableWear.setRainAndRainClothes();
        } else if (weather.getPrecipationAccumulated() >= 0.1 && weather.getPrecipationPropability() > 20) {
            comfortableWear.setPossibleRain();
        }
    }
}
