package com.mySportPage.rest;

import com.mySportPage.model.Country;
import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("country")
public class CountryRest {

    @Autowired
    private CountryService countryService;


    @GetMapping
    private SportPageResponse getCountries() {
        return SportPageResponse.builder()
                .withData(countryService.getCountries())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("name/{country}")
    private SportPageResponse getCountriesByName(
            @PathVariable String country) {
        return validateParam(country) ?
                SportPageResponse.builder()
                        .withData(countryService.getCountriesByName(country))
                        .withCode(HttpStatus.OK.value())
                        .withMessage(HttpStatus.OK.getReasonPhrase())
                        .build() :
                SportPageResponse.builder()
                        .withCode(HttpStatus.BAD_REQUEST.value())
                        .withMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .build();

    }

    @GetMapping({"code/{countryCode}"})
    private SportPageResponse getCountriesByCode(
            @PathVariable String countryCode) {
        return validateParam(countryCode) ?
                SportPageResponse.builder()
                        .withData(countryService.getCountriesByCode(countryCode))
                        .withCode(HttpStatus.OK.value())
                        .withMessage(HttpStatus.OK.getReasonPhrase())
                        .build() :
                SportPageResponse.builder()
                        .withCode(HttpStatus.BAD_REQUEST.value())
                        .withMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .build();
    }

    private boolean validateParam(String param) {
        if (param == null || param.isEmpty()) {
            return false;
        }
        List<Country> countries = countryService.getCountries();
        return countries.stream()
                .anyMatch(v -> v.getName().equalsIgnoreCase(param.trim()) || v.getCode().equalsIgnoreCase(param.trim()));
    }
}