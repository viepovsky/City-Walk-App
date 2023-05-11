package com.viepovsky.localization;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
@Service
class CityService extends BaseCityService {
    private static final String CITIES_JSON_PATH = "src/main/resources/cities.json";
    private List<City> cities;
    CityService() {
        init();
    }

    List<City> getAll() {
        return cities;
    }

    List<City> getByCode(String code) {
        return super.filterByCode(code, cities);
    }

    String getLatitude(String cityName) {
        var city = super.filterByName(cityName, cities);
        return city.getLatitude();
    }

    String getLongitude(String cityName) {
        var city = super.filterByName(cityName, cities);
        return city.getLongitude();
    }

    private void init() {
        var file = new File(CITIES_JSON_PATH);
        cities = super.getAll(file);
    }
}
