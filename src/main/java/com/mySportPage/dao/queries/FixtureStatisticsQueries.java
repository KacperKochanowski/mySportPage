package com.mySportPage.dao.queries;

import lombok.Getter;

@Getter
public enum FixtureStatisticsQueries {

    CORE_COLUMNS(" fs.shots_on_goal, fs.shots_off_goal, fs.total_shots, fs.blocked_shots, fs.shots_inside_box, " +
            "fs.shots_outside_box, fs.fouls, fs.corner_kicks, fs.offsides, fs.ball_possession, fs.yellow_cards, fs.red_cards, fs.goalkeeper_saves, " +
            "fs.total_passes, fs.passes_accurate, fs.passes_percent, fs.expected_goals, fs.team_id "),

    GET_FIXTURES_BY_FIXTURE_ID("SELECT " + CORE_COLUMNS.getQuery() +
                               "FROM {schema}.fixture_statistics fs " +
                               "WHERE fs.fixture_id = :fixtureId");

    private final String query;

    FixtureStatisticsQueries(String query) {
        this.query = query;
    }
}
