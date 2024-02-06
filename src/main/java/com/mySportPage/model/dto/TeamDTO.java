package com.mySportPage.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamDTO {

    private String name;

    private String shortCut;

    private String country;

    private Integer clubFounded;

    private Integer leagueId;

}
