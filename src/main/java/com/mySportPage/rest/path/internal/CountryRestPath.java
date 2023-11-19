package com.mySportPage.rest.path.internal;

import static com.mySportPage.rest.path.internal.CommonRestParams.COUNTRY;
import static com.mySportPage.rest.path.internal.CommonRestParams.COUNTRY_CODE;

public interface CountryRestPath {

    String ROOT_PATH = "/country";
    String GET_ALL_COUNTRIES = "";
    String GET_COUNTRY_BY_NAME = "/name/{" + COUNTRY + "}";
    String GET_COUNTRY_BY_CODE = "/code/{" + COUNTRY_CODE + "}";
}
