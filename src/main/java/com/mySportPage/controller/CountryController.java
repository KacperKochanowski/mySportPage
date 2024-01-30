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
        return service.getCountriesByName(country);
    }

    public List<Country> getCountriesByCode(String countryCode) {
        return service.getCountriesByCode(countryCode);
    }
}
