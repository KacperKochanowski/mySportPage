package com.mySportPage.dataAcquisition.model;

import com.mySportPage.team.model.Team;
import lombok.Data;

import java.util.Date;

@Data
public class Standing {

    private League league;

    private Integer season;

    private Integer rank;

    private Team team;

    private Integer points;

    private String goalsDiff;

    private String form;

    private String additionalPositionDescription;

    private Results results;

    private Results homeResults;

    private Results awayResults;

    private Date updated;
}
