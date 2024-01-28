package com.mySportPage.rest;

import com.mySportPage.controller.StadiumController;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;
import static com.mySportPage.rest.path.internal.StadiumRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class StadiumRestService {

    @Autowired
    private StadiumController controller;


    //TODO logika wielu sprawdzeń do controllera

    @GetMapping(GET_BY_CITY)
    public SportPageResponse getByCity(
            @PathVariable(CITY) String city) {
        return SportPageResponse.builder()
                .withData(city != null ? controller.getByCity(city) : new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_ADDRESS)
    public SportPageResponse getByAddress(
            @PathVariable(ADDRESS) String address) {
        return SportPageResponse.builder()
                .withData(address != null ? controller.getByAddress(address) : new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_TEAM_ID)
    public SportPageResponse getByTeamId(
            @PathVariable(TEAM_ID) Integer teamId) {
        return SportPageResponse.builder()
                .withData(teamId != null ? controller.getByTeamId(teamId) : new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_TEAM_NAME)
    public SportPageResponse getByTeamName(
            @PathVariable(TEAM_NAME) String teamName) {
        return SportPageResponse.builder()
                .withData(teamName != null ? controller.getByTeamName(teamName) : new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_NAME)
    public SportPageResponse getByName(
            @PathVariable(NAME) String name) {
        return SportPageResponse.builder()
                .withData(name != null ? controller.getByName(name) : new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
