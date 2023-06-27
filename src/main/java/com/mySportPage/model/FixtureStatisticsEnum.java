package com.mySportPage.model;

import lombok.Getter;

@Getter
public enum FixtureStatisticsEnum {
    SHOTS_ON_GOAL("Shots on Goal", "shots_on_goal", Integer.class),
    SHOTS_OFF_GOAL("Shots off Goal", "shots_off_goal", Integer.class),
    TOTAL_SHOTS("Total Shots", "total_shots", Integer.class),
    BLOCKED_SHOTS("Blocked Shots", "blocked_shots", Integer.class),
    SHOTS_INSIDE_BOX("Shots insidebox", "shots_inside_box", Integer.class),
    SHOTS_OUTSIDE_BOX("Shots outsidebox", "shots_outside_box", Integer.class),
    FOULS("Fouls", "fouls", Integer.class),
    CORNER_KICKS("Corner Kicks", "corner_kicks", Integer.class),
    OFFSIDES("Offsides", "offsides", Integer.class),
    BALL_POSSESSION("Ball Possession", "ball_possession", String.class),
    YELLOW_CARDS("Yellow Cards", "yellow_cards", Integer.class),
    RED_CARDS("Red Cards", "red_cards", Integer.class),
    GOALKEEPER_SAVES("Goalkeeper Saves", "goalkeeper_saves", Integer.class),
    TOTAL_PASSES("Total passes", "total_passes", Integer.class),
    PASSES_ACCURATE("Passes accurate", "passes_accurate", Integer.class),
    CORRECT_PASSES_PERCENT("Passes %", "passes_percent", String.class),
    EXPECTED_GOALS("expected_goals", "expected_goals", Double.class); //it's not a mistake, feed provider named this field weirdly.


    private final String feedProvidersFieldDescription;
    private final String databaseColumnName;
    private final Class<?> valueType;

    FixtureStatisticsEnum(String feedProvidersFieldDescription, String databaseColumnName, Class<?> valueType) {
        this.feedProvidersFieldDescription = feedProvidersFieldDescription;
        this.databaseColumnName = databaseColumnName;
        this.valueType = valueType;
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
