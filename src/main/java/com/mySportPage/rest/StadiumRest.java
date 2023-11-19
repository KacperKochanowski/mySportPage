package com.mySportPage.rest;

import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static com.mySportPage.rest.path.internal.StadiumRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class StadiumRest {

    @Autowired
    private StadiumService stadiumService;

    @GetMapping(GET_BY_CITY)
    public SportPageResponse getByCity(
            @PathVariable("city") String city) {
        return SportPageResponse.builder()
                .withData(city != null ? stadiumService.getByCity(city) : new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_ADDRESS)
    public SportPageResponse getByAddress(
            @PathVariable("address") String address) {
        return SportPageResponse.builder()
                .withData(address != null ? stadiumService.getByAddress(address) : new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_TEAM_ID)
    public SportPageResponse getByTeamId(
            @PathVariable("teamId") Integer teamId) {
        return SportPageResponse.builder()
                .withData(teamId != null ? stadiumService.getByTeamId(teamId) : new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_TEAM_NAME)
    public SportPageResponse getByTeamName(
            @PathVariable("teamName") String teamName) {
        return SportPageResponse.builder()
                .withData(teamName != null ? stadiumService.getByTeamName(teamName) : new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_NAME)
    public SportPageResponse getByName(
            @PathVariable("name") String name) {
        return SportPageResponse.builder()
                .withData(name != null ? stadiumService.getByName(name) : new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
