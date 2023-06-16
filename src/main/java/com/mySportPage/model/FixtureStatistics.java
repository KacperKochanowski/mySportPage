package com.mySportPage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixtureStatistics {

    private Short shotsOnGoal;
    private Short shotsOffGoal;
    private Short shotsInsideBox;
    private Short shotsOutsideBox;
    private Short totalShots;
    private Short blockedShots;
    private Short fouls;
    private Short cornerKicks;
    private Short offsides;
    private Double ballPossession;
    private Short yellowCards;
    private Short redCards;
    private Short goalkeeperSaves;
    private Short totalPasses;
    private Short passesAccurate;
    private String correctPassesPercent;
}
