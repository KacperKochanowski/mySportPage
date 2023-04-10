package com.mySportPage.fixture.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FixtureDTO {

    private Integer leagueId;

    private Integer round;

    private Date start;

    private String event;

    private boolean finished;

    private String result;
}
