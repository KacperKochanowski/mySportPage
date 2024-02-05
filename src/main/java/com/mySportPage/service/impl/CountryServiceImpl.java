package com.mySportPage.service.impl;

import com.mySportPage.dao.CountryDao;
import com.mySportPage.model.Country;
import com.mySportPage.service.CountryService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mySportPage.comonTools.TimeUnits.HOUR;

@Service
public class CountryServiceImpl implements CountryService {

    private final String ALL_COUNTRIES = "allCountries";

    private final CountryDao dao;

    @Autowired
    public CountryServiceImpl(CountryDao dao) {
        this.dao = dao;
    }

    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = HOUR)
    public void evictCaches() {
        Objects.requireNonNull(cacheManager.getCache(ALL_COUNTRIES)).clear();
    }

    @Override
    @PostConstruct
    @Cacheable(ALL_COUNTRIES)
    public List<Country> getCountries() {
        return dao.getCountries();
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
