package com.viepovsky.api.weather.dto;

import com.viepovsky.recommendation.dto.Wear;
import org.junit.jupiter.api.Test;

class WearTest {
    @Test
    void testing() {
        Wear wear = new Wear(Wear.TemperatureScale.HOT);
    }
}