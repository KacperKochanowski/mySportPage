package com.mySportPage.dao.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TeamDTO {

    private String name;

    private String shortCut;

    private String country;

    private Integer clubFounded;

}
