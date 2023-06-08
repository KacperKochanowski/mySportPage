package com.mySportPage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private String name;

    private String shortCut;

    private String country;

    private Integer clubFounded;

}
