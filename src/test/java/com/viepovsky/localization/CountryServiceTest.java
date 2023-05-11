package com.viepovsky.localization;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CountryServiceTest {
    private final CountryService service = new CountryService();

    @Test
    void should_get_countries() throws IOException {
        List<Country> countries = service.getAll();

        assertNotNull(countries);
        assertEquals(240, countries.size());
    }

    @Test
    void should_get_correct_country_code_from_countries_list() throws IOException {
        List<Country> countries = service.getAll();
        var country = countries.stream().filter(n -> n.getName().equals("Poland")).findFirst().orElse(null);

        assertNotNull(country);
        assertEquals("PL", country.getCode());
    }


}