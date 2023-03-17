package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Referee {

    @JsonProperty("referee")
    private String name;
}
