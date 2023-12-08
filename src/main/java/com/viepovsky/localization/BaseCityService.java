package com.viepovsky.localization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.exceptions.WrongArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
abstract class BaseCityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCityService.class);

    List<City> loadFromJson(File file) {
        List<City> cities = new ArrayList<>();
        var mapper = new ObjectMapper();
        try {
            City[] citiesTable = mapper.readValue(file, City[].class);
            cities = Arrays.asList(citiesTable);
        } catch (IOException e) {
            LOGGER.error("Could not retrieve cities from:{}", file.getPath());
        }
        return cities;
    }

    List<City> filterByCode(String code, List<City> cities) {
        return cities.stream()
                .filter(city -> city.getCountryCode().equals(code))
                .toList();
    }

    City filterByCodeAndName(String code, String name, List<City> cities) {
        return cities.stream()
                .filter(city -> city.getCountryCode().equals(code))
                .filter(city -> city.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new WrongArgumentException("Given city name:" + name + " does not exist."));
    }
}
