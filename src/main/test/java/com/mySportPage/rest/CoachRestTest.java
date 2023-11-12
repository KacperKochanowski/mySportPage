package com.mySportPage.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mySportPage.BaseTest;
import com.mySportPage.model.Coach;
import com.mySportPage.rest.path.CoachRestPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class CoachRestTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected String getRootPath() {
        return CoachRestPath.ROOT_PATH;
    }

    private final Coach testObject = Coach
            .builder()
            .withTeamId(348)
            .withLeagueId(106)
            .withBirthDate(mapToData("1978-10-15"))
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
        pathParams.put(CoachRestPath.PathParams.LEAGUE_ID, "106");
        String path = createPath(CoachRestPath.GET_COACH_BY_LEAGUE, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        List<Coach> coaches = mapToModel(response);

        assertThat(coaches).isNotNull();
        Optional<Coach> testCoach = coaches.stream().filter(v -> v.getName().equals("J. Gustafsson")).findFirst();
        assertThat(testCoach).isPresent()
                .hasValueSatisfying(coach -> assertThat(coach.getNationality()).isEqualTo("Sweden"))
                .hasValueSatisfying(coach -> assertThat(coach.getTeamId()).isEqualTo(348))
                .hasValueSatisfying(coach -> assertThat(coach.getLeagueId()).isEqualTo(106))
                .hasValueSatisfying(coach -> assertThat(coach.getBirthDate()).isEqualTo(mapToData("1978-10-15")))
                .hasValueSatisfying(coach -> assertThat(coach.getFirstName()).isEqualTo("Jens Otto Andreas"))
                .hasValueSatisfying(coach -> assertThat(coach.getLastName()).isEqualTo("Gustafsson"))
                .hasValueSatisfying(coach -> assertThat(coach.getPhoto()).isEqualTo("https://media-1.api-sports.io/football/coachs/1382.png"));
    }

    @Test
    void should_get_any_coach_by_team() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(CoachRestPath.PathParams.TEAM_ID, "348");
        String path = createPath(CoachRestPath.GET_COACH_BY_TEAM, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        checkReceivedValue(response);
    }

    @Test
    void should_get_any_coach_by_country() throws Exception {
        //given
        //when
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put(CoachRestPath.PathParams.COUNTRY, "Sweden");
        String path = createPath(CoachRestPath.GET_COACH_BY_COUNTRY, pathParams, null);
        MvcResult response = performGETRequest(path);
        //then
        checkReceivedValue(response);
    }

    @Test
    void should_get_any_coach_by_multiple_params() throws Exception {
        //given
        //when
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(CoachRestPath.QueryParams.LEAGUE_ID, "106");
        queryParams.put(CoachRestPath.QueryParams.TEAM_ID, "348");
        queryParams.put(CoachRestPath.QueryParams.COUNTRY, "Sweden");
        String path = createPath(CoachRestPath.GET_COACH_BY_MULTIPLE_PARAMS, null, queryParams);
        MvcResult response = performGETRequest(path);
        //then
        checkReceivedValue(response);
    }

    private void checkReceivedValue(MvcResult response) throws UnsupportedEncodingException, JsonProcessingException {
        List<Coach> coaches = mapToModel(response);
        assertThat(coaches).isNotNull();
        Optional<Coach> foundCoach = coaches.stream().filter(v -> v.getName().equals(testObject.getName())).findFirst();
        assertThat(foundCoach).isPresent()
                .hasValueSatisfying(sameObjects -> assertThat(foundCoach.get()).isEqualTo(testObject));
    }

    private List<Coach> mapToModel(MvcResult result) throws UnsupportedEncodingException, JsonProcessingException {
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode dataNode = rootNode.path("data");

        return objectMapper.readValue(dataNode.toString(), new TypeReference<>() {
        });
    }

    private Date mapToData(String date) {
        LocalDate localDate = LocalDate.parse(date);
        long milliseconds = localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        return new Date(milliseconds);
    }
}
