package com.mySportPage.dao.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LeagueDTO {

    private String name;

    private String type;

    private String country;
}
