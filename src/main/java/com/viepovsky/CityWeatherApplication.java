package com.viepovsky;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class CityWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(CityWeatherApplication.class, args);
    }

}
