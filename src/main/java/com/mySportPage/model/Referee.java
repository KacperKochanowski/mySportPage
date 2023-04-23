package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Referee {

    @JsonProperty("referee")
    private String name;

    private Country country;


    /**
     * Data forwarded by feed provider is unpredictable sometimes therefore wierd constructor needs to be used.
     */
    public Referee(String data) {
        if(data != null && data.contains(",")) {
            this.name = data.substring(0, data.lastIndexOf(","));
            this.country = new Country(data.substring(data.lastIndexOf(",")));
        } else if (data != null) {
            this.name = data;
        } else {
            new Referee();
        }
    }
}
