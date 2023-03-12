package com.mySportPage.model;

import lombok.Data;

@Data
public class LeagueCoverage {

    private Integer externalLeagueId;

    private boolean events;

    private boolean lineups;

    private boolean statisticsFixtures;

    private boolean statisticsPlayers;

    private boolean standings;

    private boolean players;

    private boolean topScorers;

    private boolean topAssists;

    private boolean topCards;

    private boolean injuries;

    private boolean predictions;

    private boolean odds;
}
