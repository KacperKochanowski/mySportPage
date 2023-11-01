package com.mySportPage.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mySportPage.model.CoachCareer;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class CoachCareerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void should_get_coach_career_by_id() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/coach-career/id/1382"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.data", Matchers.hasSize(Matchers.greaterThan(0))))
                .andExpect(jsonPath("$.data[-1].team.id", Matchers.is(766)))
                .andExpect(jsonPath("$.data[-1].start", Matchers.is("2011-07-01")))
                .andExpect(jsonPath("$.data[-1].end", Matchers.is("2014-12-01")));
    }

    @Test
    void should_get_coach_career_by_name() throws Exception {
        //given
        //when
        MvcResult response = mockMvc.perform(get("/coach-career/name/Gustafsson"))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        //then
        List<CoachCareer> coachCareerList = mapToModel(response);

        assertThat(coachCareerList).isNotNull();
        assertThat(coachCareerList.stream().map(v -> v.getTeam().getName()).collect(Collectors.toList())).contains("Pogon Szczecin");
    }


    private List<CoachCareer> mapToModel(MvcResult result) throws JsonProcessingException, UnsupportedEncodingException {
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode dataNode = rootNode.path("data");

        return objectMapper.readValue(dataNode.toString(), new TypeReference<>() {
        });
    }
}
