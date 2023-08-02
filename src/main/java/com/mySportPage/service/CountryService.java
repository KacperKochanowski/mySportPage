package com.mySportPage.service;

import com.mySportPage.model.Country;

import java.util.List;

public interface CountryService {

    List<Country> getCountries();
    List<Country> getCountriesByName(String country);
    List<Country> getCountriesByCode(String countryCode);
}
