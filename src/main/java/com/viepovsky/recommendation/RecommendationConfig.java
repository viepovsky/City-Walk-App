package com.viepovsky.recommendation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
class RecommendationConfig {
    @Value("${serverUrl}")
    private String serverUrl;
}
