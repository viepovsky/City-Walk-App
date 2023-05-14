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
        LOGGER.info("Starting to fetch air quality.");
        return client.fetchAirQuality(latitude, longitude);
    }
}
