package com.mySportPage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class StandingsDTO {

    private Short rank;
    private String teamName;
    private Short roundsPlayed;
    private Short wins;
    private Short draws;
    private Short loses;
    private String goalsRatio;
    private Short goalsDiff;
    private Short points;
    private List<String> form;
    private String positionDescription;

}
