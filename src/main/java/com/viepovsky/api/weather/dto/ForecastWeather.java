package com.viepovsky.api.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastWeather {
    private LocalDate date;
    private String symbol;
    private String symbolPhrase;
    private int maxTemp;
    private int minTemp;
    private int maxFeelsLikeTemp;
    private int minFeelsLikeTemp;
    private int maxDewPoint;
    private int minDewPoint;
    private int maxWindSpeed;
    private int uvIndex;
    private double pressure;
    private int visibility;
    @JsonProperty("precipAccum")
    private double precipationAccumulated;
    @JsonProperty("precipProb")
    private int precipationPropability;
}
