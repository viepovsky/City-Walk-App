package com.viepovsky.api.airquality;

import com.viepovsky.api.airquality.dto.AirQuality;
import com.viepovsky.exceptions.AirQualityUnavailableException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AirQualityServiceTest {
    @InjectMocks
    private AirQualityService service;
    @Mock
    private AirQualityClient client;

    @Test
    void should_get_air_quality() throws AirQualityUnavailableException {
        var airQuality = new AirQuality();
        when(client.fetchAirQuality(anyString(), anyString())).thenReturn(airQuality);

        var retrievedAirQuality = service.getAirQuality("50", "20");

        assertNotNull(retrievedAirQuality);
        verify(client, times(1)).fetchAirQuality(anyString(), anyString());
    }
}