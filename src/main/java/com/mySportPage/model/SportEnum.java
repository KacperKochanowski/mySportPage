package com.mySportPage.model;


public enum SportEnum {

    FOOTBALL(1, "football");

    private final int id;
    private final String schema;

    SportEnum(int id, String schema) {
        this.id = id;
        this.schema = schema;
    }

    public static SportEnum getById(int id) {
        for (SportEnum value : SportEnum.values()) {
            if(value.getId() == id){
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid sportEnum id!");
    }

    public int getId() {
        return id;
    }

    public String getSchema() {
        return schema;
    }
}
