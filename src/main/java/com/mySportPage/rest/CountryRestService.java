package com.mySportPage.rest;

import com.mySportPage.controller.CountryController;
import com.mySportPage.model.Country;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mySportPage.rest.path.internal.CommonRestParams.COUNTRY;
import static com.mySportPage.rest.path.internal.CommonRestParams.COUNTRY_CODE;
import static com.mySportPage.rest.path.internal.CountryRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class CountryRestService {

    private final CountryController controller;

    @Autowired
    public CountryRestService(CountryController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_ALL_COUNTRIES)
    private SportPageResponse getCountries() {
        return SportPageResponse.builder()
                .withData(controller.getCountries())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    //TODO: do controllera
    @GetMapping(GET_COUNTRY_BY_NAME)
    private SportPageResponse getCountriesByName(
            @PathVariable(COUNTRY) String country) {
        return validateParam(country) ?
                SportPageResponse.builder()
                        .withData(controller.getCountriesByName(country))
                        .withCode(HttpStatus.OK.value())
                        .withMessage(HttpStatus.OK.getReasonPhrase())
                        .build() :
                SportPageResponse.builder()
                        .withCode(HttpStatus.BAD_REQUEST.value())
                        .withMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .build();

    }

    @GetMapping(GET_COUNTRY_BY_CODE)
    private SportPageResponse getCountriesByCode(
            @PathVariable(COUNTRY_CODE) String countryCode) {
        return validateParam(countryCode) ?
                SportPageResponse.builder()
                        .withData(controller.getCountriesByCode(countryCode))
                        .withCode(HttpStatus.OK.value())
                        .withMessage(HttpStatus.OK.getReasonPhrase())
                        .build() :
                SportPageResponse.builder()
                        .withCode(HttpStatus.BAD_REQUEST.value())
                        .withMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .build();
    }

    //TODO: move to controller
    private boolean validateParam(String param) {
        if (param == null || param.isEmpty()) {
            return false;
        }
        List<Country> countries = controller.getCountries();
        return countries.stream()
                .filter(v -> v.getCode() != null)
                .filter(v -> v.getName() != null)
                .anyMatch(v -> v.getName().equalsIgnoreCase(param.trim()) || v.getCode().equalsIgnoreCase(param.trim()));
    }
}
