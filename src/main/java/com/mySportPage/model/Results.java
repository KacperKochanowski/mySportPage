package com.mySportPage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class Results {

    private Team team;

    private Integer roundsPlayed;

    private Integer wins;

    private Integer draws;

    private Integer loses;

    private Map<String, Integer> goals;

    private String description;
}

