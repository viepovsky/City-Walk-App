package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.CurrentWeather;
import com.viepovsky.api.weather.dto.Forecast;
import com.viepovsky.api.weather.dto.ForecastWeather;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class WeatherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherClient client;

    CurrentWeather fetchCurrentWeather(String latitude, String longitude) {
        LOGGER.info("Starting to fetch current weather.");
        Forecast forecast = client.fetchCurrentWeather(longitude, latitude);
        return Optional.ofNullable(forecast.getCurrentWeather()).orElse(new CurrentWeather());
    }

    List<ForecastWeather> fetchForecastWeather(String latitude, String longitude) {
        LOGGER.info("Starting to fetch forecast.");
        Forecast forecast = client.fetchForecastWeather(longitude, latitude);
        return Optional.ofNullable(forecast.getForecastWeathers()).orElse(new ArrayList<>());
    }
}
