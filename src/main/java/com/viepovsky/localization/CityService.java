package com.viepovsky.localization;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
class CityService extends BaseCityService {
    private static final String CITIES_JSON_PATH = "src/main/resources/cities.json";
    private static CityService instance = null;
    private List<City> cities;

    private CityService() {
        init();
    }

    static CityService getInstance() {
        if (instance == null) {
            instance = new CityService();
        }
        return instance;
    }

    List<City> getAll() {
        return cities;
    }

    List<City> getByCode(String code) {
        return super.filterByCode(code, cities);
    }

    City getCity(String code, String cityName) {
        return super.filterByCodeAndName(code, cityName, cities);
    }

    private void init() {
        var file = new File(CITIES_JSON_PATH);
        cities = super.loadFromJson(file);
    }
}
