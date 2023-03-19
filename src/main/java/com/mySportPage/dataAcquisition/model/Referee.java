package com.mySportPage.dataAcquisition.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Referee {

    @JsonProperty("referee")
    private String name;

    private Country country;

    public Referee(String data) {
        if(data != null) {
            this.name = data.substring(0, data.lastIndexOf(","));
            this.country = new Country(data.substring(data.lastIndexOf(",")));
        } else {
            new Referee();
        }
    }
}
