package com.mySportPage.dataAcquisition.model;

import lombok.Getter;

@Getter
public enum SportEnum {

    FOOTBALL(1, "football");

    private final Integer id;
    private final String schema;

    SportEnum(int id, String schema) {
        this.id = id;
        this.schema = schema;
    }

    public static SportEnum getById(Integer id) {
        for (SportEnum value : SportEnum.values()) {
            if(value.id.equals(id)){
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid sportEnum id!");
    }
}
