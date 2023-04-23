package com.mySportPage.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LeagueCoverage {

    private Integer externalLeagueId;

    private boolean withEvents;

    private boolean withLineups;

    private boolean withStatisticsFixtures;

    private boolean withStatisticsPlayers;

    private boolean withStandings;

    private boolean withPlayers;

    private boolean withTopScorers;

    private boolean withTopAssists;

    private boolean withTopCards;

    private boolean withInjuries;

    private boolean withPredictions;

    private boolean withOdds;
}
