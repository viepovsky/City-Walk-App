package com.viepovsky.api.airquality.dto;

import lombok.Getter;

@Getter
public class Walk {
    private final Recommendation recommendation;
    private final AirQualityIndexScale aqiScale;
    private final UvIndexScale uvIndexScale;

    public Walk(AirQualityIndexScale aqiScale, UvIndexScale uvIndexScale) {
        this.aqiScale = aqiScale;
        this.uvIndexScale = uvIndexScale;
        if (aqiScale.equals(AirQualityIndexScale.GOOD) && (uvIndexScale.equals(UvIndexScale.LOW) || uvIndexScale.equals(UvIndexScale.MODERATE))) {
            recommendation = Recommendation.RECOMMENDED;
        } else if (aqiScale.equals(AirQualityIndexScale.MODERATE) && !uvIndexScale.equals(UvIndexScale.VERY_HIGH) && !uvIndexScale.equals(UvIndexScale.EXTREME)) {
            recommendation = Recommendation.MODERATE;
        } else if ((aqiScale.equals(AirQualityIndexScale.UNHEALTHY_FOR_SENSITIVE_GROUPS) || aqiScale.equals(AirQualityIndexScale.UNHEALTHY)) && !uvIndexScale.equals(UvIndexScale.EXTREME)) {
            recommendation = Recommendation.NOT_RECOMMENDED;
        } else if (aqiScale.equals(AirQualityIndexScale.VERY_UNHEALTHY) || aqiScale.equals(AirQualityIndexScale.HAZARDOUS)) {
            recommendation = Recommendation.ONLY_IF_MUST;
        } else {
            recommendation = Recommendation.ONLY_IF_MUST;
        }
    }

    public enum Recommendation {
        RECOMMENDED,
        MODERATE,
        NOT_RECOMMENDED,
        ONLY_IF_MUST
    }

    public enum AirQualityIndexScale {
        GOOD,
        MODERATE,
        UNHEALTHY_FOR_SENSITIVE_GROUPS,
        UNHEALTHY,
        VERY_UNHEALTHY,
        HAZARDOUS
    }

    public enum UvIndexScale {
        LOW,
        MODERATE,
        HIGH,
        VERY_HIGH,
        EXTREME
    }
}
