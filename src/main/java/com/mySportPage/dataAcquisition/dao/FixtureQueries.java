package com.mySportPage.dataAcquisition.dao;

import lombok.Getter;

@Getter
public enum FixtureQueries {

    GET_FIXTURES(" SELECT event, round, result" +
                 " FROM fixture" +
                 " GROUP BY event, round, result" +
                 " HAVING round = MIN(CAST((SELECT round FROM fixture WHERE played = false LIMIT 1) AS integer))");

    private final String query;

    FixtureQueries(String query) {
        this.query = query;
    }
}
