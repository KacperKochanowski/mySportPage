package com.mySportPage.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mySportPage.BaseTest;
import com.mySportPage.model.Country;
import com.mySportPage.rest.path.internal.CountryRestPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mySportPage.rest.path.internal.CommonRestParams.COUNTRY;
import static com.mySportPage.rest.path.internal.CommonRestParams.COUNTRY_CODE;
import static com.mySportPage.rest.path.internal.CountryRestPath.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
public class CountryRestTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    private final Country TEST_COUNTRY = Country
            .builder()
            .withCode("PL")
            .withName("Poland")
            .withFlag("https://media-3.api-sports.io/flags/pl.svg")
            .build();

    @Override
    protected String getRootPath() {
        return CountryRestPath.ROOT_PATH;
    }

    @Test
    void should_get_all_countries() throws Exception {
        //given
        //when
        String path = createPath(GET_ALL_COUNTRIES, null, null);
        MvcResult response = performGETRequest(path);
        //then
        List<Country> countries = mapInternalResponse(response, new TypeReference<>() {
        });

        assertThat(countries).isNotNull();
        assertThat(countries).hasSizeGreaterThan(100);
        assertThat(countries.stream().map(Country::getName).toList()).contains("Poland");
    }

    @Test
    void should_get_country_by_name() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(COUNTRY, "Poland");
        String path = createPath(GET_COUNTRY_BY_NAME, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        List<Country> countries = mapInternalResponse(response, new TypeReference<>() {
        });

        assertThat(countries).isNotNull();
        assertThat(countries).hasSize(1);
        assertThat(countries.get(0)).isEqualTo(TEST_COUNTRY);
    }

    @Test
    void should_not_get_country_by_invalid_name() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(COUNTRY, "Zachodniopomorskie");
        String path = createPath(GET_COUNTRY_BY_NAME, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        List<Country> countries = mapInternalResponse(response, new TypeReference<>() {
        });

        assertThat(countries).isNull();
    }

    @Test
    void should_get_country_by_code() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(COUNTRY_CODE, "PL");
        String path = createPath(GET_COUNTRY_BY_CODE, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        List<Country> countries = mapInternalResponse(response, new TypeReference<>() {
        });

        assertThat(countries).isNotNull();
        assertThat(countries).hasSizeGreaterThan(0);
        assertThat(countries.stream().filter(v -> v.equals(TEST_COUNTRY)).collect(Collectors.toList())).hasSizeGreaterThan(0);
        assertThat(countries.stream().map(Country::getCode).collect(Collectors.toList())).doesNotContain("PT");
    }

    @Test
    void should_not_get_country_by_invalid_code() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(COUNTRY_CODE, "Zachodniopomorskie");
        String path = createPath(GET_COUNTRY_BY_CODE, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        List<Country> countries = mapInternalResponse(response, new TypeReference<>() {
        });

        assertThat(countries).isNull();
    }
}
