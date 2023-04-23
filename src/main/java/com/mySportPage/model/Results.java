package com.mySportPage.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class Results {

    private Team team;

    private Integer roundsPlayed;

    private Integer wins;

    private Integer draws;

    private Integer loses;

    private Map<String, Integer> goals;

    private String description;
}
