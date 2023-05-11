package com.viepovsky.localization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
class CountryService {
    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);

    List<Country> getAllCountries() throws IOException {
        var mapper = new ObjectMapper();
        List<Country> countries = new ArrayList<>();
        try {
            var file = new File("src/main/resources/countries.json");
            Country[] countryTable = mapper.readValue(file, Country[].class);
            countries = Arrays.asList(countryTable);
        } catch (IOException e) {
            logger.error("Could not get countries.");
        }
        return countries;
    }
}
