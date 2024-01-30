package com.mySportPage.service.impl;

import com.mySportPage.dao.CountryDao;
import com.mySportPage.model.Country;
import com.mySportPage.service.CountryService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryDao dao;

    @Autowired
    public CountryServiceImpl(CountryDao dao) {
        this.dao = dao;
    }

    private List<Country> cachedCountries;

    @Override
    @PostConstruct
    public List<Country> getCountries() {
        if (cachedCountries == null) {
            cachedCountries = dao.getCountries();
        }
        return cachedCountries;
    }

    @Override
    public List<Country> getCountriesByName(String country) {
        return cachedCountries.stream()
                .filter(v -> v.getName().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }

    @Override
    public List<Country> getCountriesByCode(String countryCode) {
        return cachedCountries.stream()
                .filter(v -> v.getCode() != null)
                .filter(v -> v.getCode().equalsIgnoreCase(countryCode))
                .collect(Collectors.toList());
    }
}
