package com.viepovsky.recommendation;

import com.viepovsky.recommendation.dto.Walk;
import com.viepovsky.recommendation.dto.Wear;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("city-weather-app/recommendation")
@RequiredArgsConstructor
@Validated
public class RecommendationController {
    private final RecommendationService service;

    @GetMapping(path = "/walk")
    ResponseEntity<Walk> getWalkRecommendation(
            @RequestParam(name = "latitude") @NotBlank String latitude,
            @RequestParam(name = "longitude") @NotBlank String longitude
    ) {
        return ResponseEntity.ok(service.getWalkRecommendation(latitude, longitude));
    }

    @GetMapping(path = "/wear")
    ResponseEntity<Wear> getWearRecommendation(
            @RequestParam @FutureOrPresent @NotNull LocalDate date,
            @RequestParam(name = "latitude") @NotBlank String latitude,
            @RequestParam(name = "longitude") @NotBlank String longitude
    ) {
        if (LocalDate.now().plusDays(11).isBefore(date)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.getWearRecommendation(date, latitude, longitude));
    }
}
