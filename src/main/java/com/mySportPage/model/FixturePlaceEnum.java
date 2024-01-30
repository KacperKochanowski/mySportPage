package com.mySportPage.model;

import lombok.Getter;

@Getter
public enum FixturePlaceEnum {
    HOME("home"),
    AWAY("away");

    FixturePlaceEnum(String description1) {
        this.description = description1;
    }

    private final String description;

    public static FixturePlaceEnum getByDescription(String description) {
        if (description != null && !description.isEmpty()) {
            for (FixturePlaceEnum place : FixturePlaceEnum.values()) {
                if (place.getDescription().equals(description)) {
                    return place;
                }
            }
        }
        return null;
    }
}
