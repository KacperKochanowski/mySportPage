package com.mySportPage.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mySportPage.BaseTest;
import com.mySportPage.cache.CoachContainer;
import com.mySportPage.model.Coach;
import com.mySportPage.model.request.CoachRequestModel;
import com.mySportPage.rest.path.internal.CoachRestPath;
import com.mySportPage.task.CoachTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.mySportPage.comonTools.Formatter.mapToDate;
import static com.mySportPage.rest.path.internal.CoachRestPath.*;
import static com.mySportPage.rest.path.internal.CommonRestParams.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class CoachRestTest extends BaseTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CoachTask timerTask;

    @Override
    protected String getRootPath() {
        return CoachRestPath.ROOT_PATH;
    }

    @BeforeEach
    public void init() {
        if (CoachContainer.getCoaches().isEmpty()) {
            timerTask.doWork();
        }
    }

    private final Coach testObject = Coach
            .builder()
            .withTeamId(348)
            .withLeagueId(106)
            .withBirthDate(mapToDate("1978-10-15"))
            .withName("J. Gustafsson")
            .withFirstName("Jens Otto Andreas")
            .withLastName("Gustafsson")
            .withNationality("Sweden")
            .withPhoto("https://media-1.api-sports.io/football/coachs/1382.png")
            .build();

    @Test
    void should_get_any_coach_by_league() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(LEAGUE_ID, "106");
        String path = createPath(GET_COACH_BY_LEAGUE, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        List<Coach> coaches = mapInternalResponse(response, new TypeReference<>() {
        });
        assertThat(coaches).isNotNull();
        Optional<Coach> testCoach = coaches.stream().filter(v -> v.getName().equals("J. Gustafsson")).findFirst();
        assertThat(testCoach).isPresent()
                .hasValueSatisfying(coach -> assertThat(coach.getNationality()).isEqualTo("Sweden"))
                .hasValueSatisfying(coach -> assertThat(coach.getTeamId()).isEqualTo(348))
                .hasValueSatisfying(coach -> assertThat(coach.getLeagueId()).isEqualTo(106))
                .hasValueSatisfying(coach -> assertThat(coach.getBirthDate()).isEqualTo(mapToDate("1978-10-15")))
                .hasValueSatisfying(coach -> assertThat(coach.getFirstName()).isEqualTo("Jens Otto Andreas"))
                .hasValueSatisfying(coach -> assertThat(coach.getLastName()).isEqualTo("Gustafsson"))
                .hasValueSatisfying(coach -> assertThat(coach.getPhoto()).isEqualTo("https://media-1.api-sports.io/football/coachs/1382.png"));
    }

    @Test
    void should_get_any_coach_by_team() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(TEAM_ID, "348");
        String path = createPath(GET_COACH_BY_TEAM, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        checkReceivedValue(response);
    }

    @Test
    void should_get_any_coach_by_country() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(COUNTRY, "Sweden");
        String path = createPath(GET_COACH_BY_COUNTRY, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        checkReceivedValue(response);
    }

    @Test
    void should_get_any_coach_by_multiple_params() throws Exception {
        //given
        //when
        CoachRequestModel requestModel = CoachRequestModel.builder()
                .withLeagueId(106)
                .withTeamId(348)
                .withCountry("Sweden")
                .build();
        String content = objectMapper.writeValueAsString(requestModel);
        MvcResult response = performPOSTRequestWithContent(getRootPath(), content);
        //then
        checkReceivedValue(response);
    }

    private void checkReceivedValue(MvcResult response) throws UnsupportedEncodingException, JsonProcessingException {
        List<Coach> coaches = mapInternalResponse(response, new TypeReference<>() {
        });
        assertThat(coaches).isNotEmpty();
        Optional<Coach> foundCoach = coaches.stream().filter(v -> v.getName().equals(testObject.getName())).findFirst();
        assertThat(foundCoach).isPresent()
                .hasValueSatisfying(sameObjects -> assertThat(foundCoach.get()).isEqualTo(testObject));
    }
}
