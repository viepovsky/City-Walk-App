package com.viepovsky.recommendation;

import com.viepovsky.recommendation.dto.Walk;
import com.viepovsky.recommendation.dto.Wear;
import org.hamcrest.Matchers;
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

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(RecommendationController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService service;

    @Test
    void should_get_walk_recommendation() throws Exception {
        Walk walkRecommendation = new Walk(Walk.AirQualityIndexScale.GOOD, Walk.UvIndexScale.MODERATE);
        when(service.getWalkRecommendation(anyString(), anyString())).thenReturn(walkRecommendation);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-weather-app/recommendation/walk")
                        .param("latitude", "50.22")
                        .param("longitude", "5.22"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()));
    }

    @Test
    void should_get_wear_recommendation() throws Exception {
        Wear wearRecommendation = new Wear(Wear.WeatherDescription.SCORCHING_HOT);
        when(service.getWearRecommendation(any(), anyString(), anyString())).thenReturn(wearRecommendation);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-weather-app/recommendation/wear")
                        .param("date", LocalDate.now().toString())
                        .param("latitude", "50.22")
                        .param("longitude", "5.22"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()));
    }

    @Test
    void should_not_get_wear_recommendation_if_invalid_params() throws Exception {
        Wear wearRecommendation = new Wear(Wear.WeatherDescription.SCORCHING_HOT);
        when(service.getWearRecommendation(any(), anyString(), anyString())).thenReturn(wearRecommendation);

        mockMvc.perform(MockMvcRequestBuilders.get("/city-weather-app/recommendation/wear")
                        .param("date", LocalDate.now().plusDays(12).toString())
                        .param("latitude", "50.22")
                        .param("longitude", "5.22"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}