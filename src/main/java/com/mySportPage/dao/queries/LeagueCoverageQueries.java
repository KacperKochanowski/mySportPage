package com.mySportPage.dao.queries;

import lombok.Getter;

@Getter
public enum LeagueCoverageQueries {

    CORE_COVERAGE("external_league_id, events, lineups, statistics_fixtures, statistics_players, standings, players, top_scorers, top_assists, top_cards, injuries, predictions, odds "),

    GET_COVERAGE("SELECT " + CORE_COVERAGE.getQuery() +
                 "FROM {schema}.league_coverage");


    private final String query;

    LeagueCoverageQueries(String query) {
        this.query = query;
    }
}
