package com.mySportPage.rest.path;

import static com.mySportPage.rest.path.CountryRestPath.PathParam.COUNTRY;
import static com.mySportPage.rest.path.CountryRestPath.PathParam.COUNTRY_CODE;

public interface CountryRestPath {

    String ROOT_PATH = "/country";
    String GET_ALL_COUNTRIES = "";
    String GET_COUNTRY_BY_NAME = "/name/" + COUNTRY;
    String GET_COUNTRY_BY_CODE = "/code/" + COUNTRY_CODE;

    interface PathParam {
        String COUNTRY = "{country}";
        String COUNTRY_CODE = "{countryCode}";
    }
}
