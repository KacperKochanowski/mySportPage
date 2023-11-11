package com.mySportPage.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mySportPage.BaseTest;
import com.mySportPage.model.Coach;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    //TODO adresy restów przerzucić do interfejsu

    @Test
    void should_get_any_coach_by_league() throws Exception {
        //given
        //when
        MvcResult response = performGETRequest("/coach/leagueId/106");
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
        MvcResult response = performGETRequest("/coach/teamId/348");
        //then
        checkReceiverValue(response);
    }

    @Test
    void should_get_any_coach_by_country() throws Exception {
        //given
        //when
        MvcResult response = performGETRequest("/coach/country/Sweden");
        //then
        checkReceiverValue(response);
    }

    @Test
    void should_get_any_coach_by_multiple_params() throws Exception {
        //given
        //when
        MvcResult response = performGETRequest("/coach?leagueId=106&teamId=348&country=Sweden");
        //then
        checkReceiverValue(response);
    }

    private void checkReceiverValue(MvcResult response) throws UnsupportedEncodingException, JsonProcessingException {
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
