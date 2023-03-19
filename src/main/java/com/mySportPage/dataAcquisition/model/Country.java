package com.mySportPage.dataAcquisition.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    private String name;

    private String code;

    private String flag;


    public Country(String name) {
        this.name = name;
    }
}
