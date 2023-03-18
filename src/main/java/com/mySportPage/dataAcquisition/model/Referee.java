package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Referee {

    @JsonProperty("referee")
    private String name;
}
