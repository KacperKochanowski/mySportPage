package com.mySportPage.service;

import com.mySportPage.dao.CountryDao;
import com.mySportPage.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    @Override
    public List<Country> getCountries() {
        return countryDao.getCountries();
    }

    @Override
    public List<Country> getCountriesByName(String country) {
        return getCountries().stream()
                .filter(v -> v.getName().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }

    @Override
    public List<Country> getCountriesByCode(String countryCode) {
        return getCountries().stream()
                .filter(v -> v.getCode() != null)
                .filter(v -> v.getCode().equalsIgnoreCase(countryCode))
                .collect(Collectors.toList());
    }
}
