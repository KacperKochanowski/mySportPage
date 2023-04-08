package com.mySportPage.dataAcquisition.dao;

import lombok.Getter;

@Getter
public enum FixtureQueries {

    /**
     * Used to fetch fixtures from specific (current) round by every league
     */
    
    GET_FIXTURES("SELECT league_id, event, start, played, result, round " +
                 "FROM fixture " +
                 "GROUP BY event, start, result, round, league_id, played " +
                 "HAVING round = MIN(CAST((SELECT round FROM fixture WHERE played = false LIMIT 1) AS integer)) ");

    private final String query;

    FixtureQueries(String query) {
        this.query = query;
    }
}
