package com.mySportPage.dataAcquisition.model;

import lombok.Getter;

@Getter
public enum SportEnum {

    FOOTBALL(1, "football");

    private final int id;
    private final String schema;

    SportEnum(int id, String schema) {
        this.id = id;
        this.schema = schema;
    }
}
