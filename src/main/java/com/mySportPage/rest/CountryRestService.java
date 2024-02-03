package com.mySportPage.rest;

import com.mySportPage.controller.CountryController;
import com.mySportPage.model.Country;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CountryRestService extends AbstractRestService {

    private final CountryController controller;

    @Autowired
    public CountryRestService(CountryController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_ALL_COUNTRIES)
    private SportPageResponse<List<Country>> getCountries() {
        return processResponse(controller::getCountries);
    }

    @GetMapping(GET_COUNTRY_BY_NAME)
    private SportPageResponse<List<Country>> getCountriesByName(
            @PathVariable(COUNTRY) String country) {
        return processResponse(() -> controller.getCountriesByName(country));
    }

    @GetMapping(GET_COUNTRY_BY_CODE)
    private SportPageResponse<List<Country>> getCountriesByCode(
            @PathVariable(COUNTRY_CODE) String countryCode) {
        return processResponse(() -> controller.getCountriesByCode(countryCode));
    }
}
