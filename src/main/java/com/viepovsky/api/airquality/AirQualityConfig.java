package com.viepovsky.api.airquality;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Getter
@Component
class AirQualityConfig {
    @Value("${airquality.api.endpoint}")
    private String airQualityApiEndpoint;
    @Value("${airquality.api.key}")
    private String airQualityApiKey;
    @Value("${airquality.api.host}")
    private String airQualityApiHost;
}
