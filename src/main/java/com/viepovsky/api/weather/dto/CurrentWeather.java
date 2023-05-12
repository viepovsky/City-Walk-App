package com.viepovsky.api.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {
    private String symbol;
    private String symbolPhrase;
    private int temperature;
    private int feelsLikeTemp;
    private int dewPoint;
    private int windSpeed;
    private int uvIndex;
    private double pressure;
    private int visibility;
}
