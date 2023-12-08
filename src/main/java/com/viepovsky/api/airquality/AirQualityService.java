package com.viepovsky.api.airquality;

import com.viepovsky.api.airquality.dto.AirQuality;
import com.viepovsky.exceptions.AirQualityUnavailableException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AirQualityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AirQualityService.class);
    private final AirQualityClient client;

    AirQuality getAirQuality(String latitude, String longitude) throws AirQualityUnavailableException {
        LOGGER.info("Fetching air quality for given latitude:{} and longitude:{}.", latitude, longitude);
        return client.fetchAirQuality(latitude, longitude);
    }
}
