package com.mySportPage.controller;

import com.mySportPage.model.Country;
import com.mySportPage.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CountryController {

    private final CountryService service;

    @Autowired
    public CountryController(CountryService service) {
        this.service = service;
    }

    public List<Country> getCountries() {
        return service.getCountries();
    }

    public List<Country> getCountriesByName(String country) {
        if (isParamValid(country)) {
            return service.getCountriesByName(country);
        }
        throw new IllegalArgumentException("Invalid country value!");
    }

    public List<Country> getCountriesByCode(String countryCode) {
        if (isParamValid(countryCode)) {
            return service.getCountriesByCode(countryCode);
        }
        throw new IllegalArgumentException("Invalid country code value!");
    }

    private boolean isParamValid(String param) {
        if (param == null || param.isEmpty()) {
            return false;
        }
        //TODO: jakiś cache na to, aby nie walić co chwilę do bazy po dane?
        List<Country> countries = service.getCountries();
        return countries.stream()
                .filter(v -> v.getCode() != null)
                .filter(v -> v.getName() != null)
                .anyMatch(v -> v.getName().equalsIgnoreCase(param.trim()) || v.getCode().equalsIgnoreCase(param.trim()));
    }
}
