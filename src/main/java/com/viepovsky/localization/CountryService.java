package com.viepovsky.localization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
class CountryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);
    private static CountryService instance;
    private List<Country> countries;

    private CountryService() {
        init();
    }

    static CountryService getInstance() {
        if (instance == null) {
            instance = new CountryService();
        }
        return instance;
    }

    List<Country> getAll() {
        return countries;
    }

    void init() {
        var mapper = new ObjectMapper();
        try {
            var file = new File("src/main/resources/countries.json");
            Country[] countryTable = mapper.readValue(file, Country[].class);
            countries = Arrays.asList(countryTable);
        } catch (IOException e) {
            LOGGER.error("Could not retrieve countries from countries.json.");
        }
    }
}
