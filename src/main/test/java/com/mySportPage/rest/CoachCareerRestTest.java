package com.mySportPage.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mySportPage.BaseTest;
import com.mySportPage.model.CoachCareer;
import com.mySportPage.rest.path.internal.CoachCareerRestPath;
import com.mySportPage.rest.response.SportPageBaseResponse;
import org.hamcrest.Matchers;
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

import static com.mySportPage.rest.path.internal.CoachCareerRestPath.GET_CAREER_BY_COACH_ID;
import static com.mySportPage.rest.path.internal.CoachCareerRestPath.GET_CAREER_BY_COACH_NAME;
import static com.mySportPage.rest.path.internal.CommonRestParams.COACH_ID;
import static com.mySportPage.rest.path.internal.CommonRestParams.COACH_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class CoachCareerRestTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected String getRootPath() {
        return CoachCareerRestPath.ROOT_PATH;
    }

    @Test
    void should_get_coach_career_by_id() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(COACH_ID, "1382");
        String path = createPath(GET_CAREER_BY_COACH_ID, pathParams, null);
        //then
        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(jsonPath("$.data[-1].team.id", Matchers.is(766)))
                .andExpect(jsonPath("$.data[-1].start", Matchers.is("2011-07-01")))
                .andExpect(jsonPath("$.data[-1].end", Matchers.is("2014-12-01")));
    }

    @Test
    void should_not_get_coach_career_by_id() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(COACH_ID, "-1382");
        String path = createPath(GET_CAREER_BY_COACH_ID, pathParams, null);
        //then
        mockMvc.perform(get(path))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data", Matchers.hasSize(Matchers.equalTo(0))));
    }

    @Test
    void should_get_coach_career_by_name() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(COACH_NAME, "J. Gustafsson");
        String path = createPath(GET_CAREER_BY_COACH_NAME, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        List<CoachCareer> coachCareerList = mapInternalResponse(response, new TypeReference<>() {
        });
        assertThat(coachCareerList).isNotNull();
        assertThat(coachCareerList.stream().map(v -> v.getTeam().getName()).collect(Collectors.toList())).contains("Pogon Szczecin");
    }

    @Test
    void should_not_get_coach_career_by_empty_name() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(COACH_NAME, "  ");
        String path = createPath(GET_CAREER_BY_COACH_NAME, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        SportPageBaseResponse coachCareerList = mapInternalResponse(response, new TypeReference<>() {
        });
        assertThat(coachCareerList).isNotNull();
        assertThat(coachCareerList.isSuccess()).isFalse();
        assertThat(coachCareerList.getErrorMessage()).isEqualTo("Forwarded param can not contain only white spaces!");
    }
}
