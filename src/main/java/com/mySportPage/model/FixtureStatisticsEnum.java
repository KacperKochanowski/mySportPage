package com.mySportPage.model;

import lombok.Getter;

@Getter
public enum FixtureStatisticsEnum {
    SHOTS_ON_GOAL("Shots on Goal", "shots_on_goal"),
    SHOTS_OFF_GOAL("Shots off Goal", "shots_off_goal"),
    TOTAL_SHOTS("Total Shots", "total_shots"),
    BLOCKED_SHOTS("Blocked Shots", "blocked_shots"),
    SHOTS_INSIDE_BOX("Shots insidebox", "shots_inside_box"),
    SHOTS_OUTSIDE_BOX("Shots outsidebox", "shots_outside_box"),
    FOULS("Fouls", "fouls"),
    CORNER_KICKS("Corner Kicks", "corner_kicks"),
    OFFSIDES("Offsides", "offsides"),
    BALL_POSSESSION("Ball Possession", "ball_possession"),
    YELLOW_CARDS("Yellow cards", "yellow_cards"),
    RED_CARDS("Red cards", "red_cards"),
    GOALKEEPER_SAVES("Goalkeeper Saves", "goalkeeper_saves"),
    TOTAL_PASSES("Total passes", "total_passes"),
    PASSES_ACCURATE("Passes accurate", "passes_accurate"),
    CORRECT_PASSES_PERCENT("Passes %", "passes_percent"),
    EXPECTED_GOALS("expected_goals", "expected_goals"); //it's not a mistake, feed provider named this field weirdly.


    private final String feedProvidersFieldDescription;
    private final String databaseColumnName;

    FixtureStatisticsEnum(String description, String databaseColumnName) {
        this.feedProvidersFieldDescription = description;
        this.databaseColumnName = databaseColumnName;
    }

    public static FixtureStatisticsEnum getByDescription(String description) {
        for (FixtureStatisticsEnum statsEnum : FixtureStatisticsEnum.values()) {
            if(statsEnum.getFeedProvidersFieldDescription().equals(description)) {
                return statsEnum;
            }
        }
        return null;
    }
}
