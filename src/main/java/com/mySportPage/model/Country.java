package com.mySportPage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Country {

    private String name;

    private String code;

    private String flag;


    public Country(String name) {
        this.name = name;
    }
}
