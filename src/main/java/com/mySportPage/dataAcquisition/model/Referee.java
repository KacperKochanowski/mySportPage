package com.mySportPage.dataAcquisition.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Referee {

    @JsonProperty("referee")
    private String name;
}
