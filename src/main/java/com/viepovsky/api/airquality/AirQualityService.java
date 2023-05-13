package com.viepovsky.api.airquality;

import com.viepovsky.api.airquality.dto.AirQuality;
import com.viepovsky.api.airquality.dto.Walk;
import com.viepovsky.api.weather.dto.CurrentWeather;
import com.viepovsky.exceptions.WrongArgumentException;
import com.viepovsky.localization.dto.CityResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
class AirQualityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirQualityService.class);
    private final AirQualityClient client;
    private final RestTemplate restTemplate;

    AirQuality getAirQuality(String code, String city) {
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/city-weather-app/localization/city")
                .queryParam("country-code", code)
                .queryParam("city", city)
                .encode()
                .build()
                .toUri();
        try {
            ResponseEntity<CityResponse> response = restTemplate.getForEntity(url, CityResponse.class);
            CityResponse cityResponse = response.getBody();
            return client.fetchAirQuality(cityResponse.getLatitude(), cityResponse.getLongitude());
        } catch (HttpClientErrorException e) {
            LOGGER.error(e.getMessage());
            throw new WrongArgumentException(e.getMessage().substring(4));
        }
    }

    public Walk getWalkRecommendation(String code, String city) {
        AirQuality airQuality = getAirQuality(code, city);
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/city-weather-app/weather")
                .queryParam("country-code", code)
                .queryParam("city", city)
                .encode()
                .build()
                .toUri();
        try {
            ResponseEntity<CurrentWeather> response = restTemplate.getForEntity(url, CurrentWeather.class);
            CurrentWeather weather = response.getBody();
            Walk.AirQualityIndexScale aqiScale = retrieveAirQualityIndexScale(airQuality.getOverallAqi());
            Walk.UvIndexScale uvIndexScale = retrieveUvIndexScale(weather.getUvIndex());
            return new Walk(aqiScale, uvIndexScale);
        } catch (HttpClientErrorException e) {
            LOGGER.error(e.getMessage());
            throw new WrongArgumentException(e.getMessage().substring(4));
        }
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
}
