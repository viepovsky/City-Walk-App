package com.viepovsky.api.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    @JsonProperty("current")
    private CurrentWeather currentWeather;
    @JsonProperty("forecast")
    private List<ForecastWeather> forecastWeathers;
}
