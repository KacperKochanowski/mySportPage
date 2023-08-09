package com.mySportPage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Do not add the @Builder annotation here!
 * It will prevent object mapping by MapStruct.
 */

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private String name;

    private String shortCut;

    private String country;

    private Integer clubFounded;

    private Integer leagueId;

}
