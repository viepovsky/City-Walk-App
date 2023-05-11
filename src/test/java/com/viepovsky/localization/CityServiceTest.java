package com.viepovsky.localization;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CityServiceTest {
    private final CityService service = CityService.getInstance();

    @Test
    void should_get_cities() {
        List<City> cities = service.getAll();

        assertNotNull(cities);
        assertEquals(140929, cities.size());
    }

    @Test
    void should_get_cities_by_country_code() {
        List<City> cities = service.getByCode("PL");

        assertNotNull(cities);
        assertEquals(2872, cities.size());
    }

    @Test
    void should_get_city() {
        var city = service.getCity("PL", "Poznań");

        assertNotNull(city);
        assertEquals("52.40692", city.getLatitude());
        assertEquals("16.92993", city.getLongitude());
    }
}