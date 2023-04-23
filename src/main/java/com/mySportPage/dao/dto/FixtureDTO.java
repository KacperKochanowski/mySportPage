package com.mySportPage.dao.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class FixtureDTO {

    private Integer leagueId;

    private String leagueName;

    private Integer round;

    private Date start;

    private String event;

    private boolean finished;

    private String result;
}
