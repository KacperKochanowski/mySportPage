package com.mySportPage.dao;

import com.mySportPage.model.Country;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CountryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Country> getCountries() {
        String query = "SELECT \"name\", code, flag FROM public.country";
        List<Object[]> countries = entityManager.createNativeQuery(query).getResultList();
        return mapToCountry(countries);
    }

    private List<Country> mapToCountry(List<Object[]> data) {
        List<Country> countries = new ArrayList<>();
        for (Object[] value : data) {
            countries.add(Country.builder()
                    .withName((String) value[0])
                    .withCode((String) value[1])
                    .withFlag((String) value[2])
                    .build());
        }
        return countries;
    }
}
