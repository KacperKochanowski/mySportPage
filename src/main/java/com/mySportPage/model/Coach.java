package com.mySportPage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class Coach {

    private Integer externalId;
    private String name;
    private String firstName;
    private String lastName;
    private int age;
    private Date birthDate;
    private String birthPlace;
    private String birthCountry;
    private String nationality;
    private Integer height;
    private Integer weight;
    private String photo;
}
