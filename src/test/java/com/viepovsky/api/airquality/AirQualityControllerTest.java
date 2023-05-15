package com.viepovsky.api.airquality;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.api.airquality.dto.AirQuality;
import com.viepovsky.exceptions.AirQualityUnavailableException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(AirQualityController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AirQualityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirQualityService service;

    @Test
    void should_get_air_quality() throws Exception, AirQualityUnavailableException {
        AirQuality airQuality = new AirQuality();
        String jsonResponse = new ObjectMapper().writeValueAsString(airQuality);
        when(service.getAirQuality(anyString(), anyString())).thenReturn(airQuality);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-walk-app/airquality")
                        .param("latitude", "50.22")
                        .param("longitude", "5.22"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }
}