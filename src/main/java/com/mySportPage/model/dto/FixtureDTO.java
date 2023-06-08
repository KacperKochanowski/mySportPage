package com.mySportPage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class FixtureDTO {

    private Integer leagueId;

    private String leagueName;

    private Integer round;

    private Date start;

    private String event;

    private boolean finished;

    private String result;
}
