package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.CurrentWeather;
import com.viepovsky.api.weather.dto.Forecast;
import com.viepovsky.api.weather.dto.ForecastWeather;
import com.viepovsky.api.weather.dto.Wear;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class WeatherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherClient client;
    private final RestTemplate restTemplate;

    CurrentWeather fetchCurrentWeather(String code, String city) {
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
            Forecast forecast = client.fetchCurrentWeather(cityResponse.getLongitude(), cityResponse.getLatitude());
            return Optional.ofNullable(forecast.getCurrentWeather()).orElse(new CurrentWeather());
        } catch (HttpClientErrorException e) {
            LOGGER.error(e.getMessage());
            throw new WrongArgumentException(e.getMessage().substring(4));
        }
    }

    List<ForecastWeather> fetchForecastWeather(String code, String city) {
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
            Forecast forecast = client.fetchForecastWeather(cityResponse.getLongitude(), cityResponse.getLatitude());
            return Optional.ofNullable(forecast.getForecastWeathers()).orElse(new ArrayList<>());
        } catch (HttpClientErrorException e) {
            LOGGER.error(e.getMessage());
            throw new WrongArgumentException(e.getMessage().substring(4));
        }
    }

    public Wear getComfortableWear(LocalDate date, String code, String city) {
        List<ForecastWeather> forecast = fetchForecastWeather(code, city);
        ForecastWeather weather = forecast.stream()
                .filter(w -> w.getDate().isEqual(date))
                .findFirst()
                .get();
        Wear comfortableWear;
        int feelsLikeTemp = weather.getMaxFeelsLikeTemp() + weather.getMinFeelsLikeTemp() / 2;
        if (feelsLikeTemp >= 40) {
            comfortableWear = new Wear(Wear.TemperatureScale.SCORCHING_HOT);
        } else if (feelsLikeTemp >= 30) {
            comfortableWear = new Wear(Wear.TemperatureScale.HOT);
        } else if (feelsLikeTemp >= 23) {
            comfortableWear = new Wear(Wear.TemperatureScale.WARM);
        } else if (feelsLikeTemp >= 18) {
            comfortableWear = new Wear(Wear.TemperatureScale.MODERATE);
        } else if (feelsLikeTemp >= 15) {
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
}