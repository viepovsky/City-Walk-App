package com.viepovsky.api.airquality.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQuality {
    @JsonProperty("CO")
    private Map<String, Integer> carbonMonoxide;

    @JsonProperty("NO2")
    private Map<String, Integer> nitrogenDioxide;

    @JsonProperty("O3")
    private Map<String, Integer> ozone;

    @JsonProperty("SO2")
    private Map<String, Integer> sulphurDioxide;

    @JsonProperty("PM2.5")
    private Map<String, Integer> pm2_5;

    @JsonProperty("PM10")
    private Map<String, Integer> pm10;

    @JsonProperty("overall_aqi")
    private String overallAqi;

}
