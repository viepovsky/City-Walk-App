package com.viepovsky.api.airquality;

import com.viepovsky.api.airquality.dto.AirQuality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AirQualityClientTest {
    @InjectMocks
    private AirQualityClient client;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AirQualityConfig config;

    @BeforeEach
    void before_each() {
        when(config.getAirQualityApiEndpoint()).thenReturn("https://test.com");
        when(config.getAirQualityApiKey()).thenReturn("testkey");
        when(config.getAirQualityApiHost()).thenReturn("testhost");
    }

    @Test
    void should_fetch_airquality() throws URISyntaxException {
        //Given
        var airQuality = new AirQuality();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", config.getAirQualityApiKey());
        headers.set("X-RapidAPI-Host", config.getAirQualityApiHost());
        HttpEntity<String> requestEntityHeaders = new HttpEntity<>(headers);

        ResponseEntity<AirQuality> response = new ResponseEntity<>(airQuality, HttpStatus.OK);

        URI url = new URI("https://test.com?lat=50.4433&lon=40.4455");
        when(restTemplate.exchange(url, HttpMethod.GET, requestEntityHeaders, AirQuality.class)).thenReturn(response);
        //When
        var retrievedAirQuality = client.fetchAirQuality("50.4433", "40.4455");
        //Then
        assertNotNull(retrievedAirQuality);
        verify(restTemplate, times(1)).exchange(url, HttpMethod.GET, requestEntityHeaders, AirQuality.class);
    }
}