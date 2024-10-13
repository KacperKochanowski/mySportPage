package com.mySportPage.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mySportPage.BaseTest;
import com.mySportPage.model.dto.FixtureDTO;
import com.mySportPage.rest.path.internal.FixtureRestPath;
import com.mySportPage.rest.path.internal.LeagueRestPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static com.mySportPage.rest.path.internal.FixtureRestPath.GET_CURRENT_FIXTURES;
import static com.mySportPage.rest.path.internal.LeagueRestPath.ANY_PLAYS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
public class FixtureRestTest extends BaseTest {


    //TODO: investigate logic
    private boolean testsAvailable = false;

    @Override
    protected String getRootPath() {
        return testsAvailable ? FixtureRestPath.ROOT_PATH : LeagueRestPath.ROOT_PATH;
    }

    @Override
    protected String createPath(String restPath, Map<String, String> pathParams, Map<String, String> queryParams) {
        StringBuilder path = new StringBuilder(getRootPath() + restPath);
        if (pathParams != null) {
            path = fillPathParams(pathParams, path);
        }
        if (queryParams != null) {
            path = fillQueryParams(queryParams, path);
        }
        return path.toString();
    }

    @Test
    void setUp() throws Exception {
        //given
        //when
        String path = createPath(ANY_PLAYS, null, null);
        MvcResult response = performGETRequest(path);
        //then
        testsAvailable = mapInternalResponse(response, new TypeReference<>() {
        });
    }

    @Test
    void should_get_current_fixtures() throws Exception {
        if (!testsAvailable) {
            return;
        }
        //given
        //when
        String path = createPath(GET_CURRENT_FIXTURES, null, null);
        MvcResult response = performGETRequest(path);
        //then
        List<FixtureDTO> fixtures = mapInternalResponse(response, new TypeReference<>() {
        });
        assertThat(fixtures).isNotNull();
    }
}
