package com.mySportPage.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
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
