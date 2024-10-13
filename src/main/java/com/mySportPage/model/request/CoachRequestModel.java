package com.mySportPage.model.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class CoachRequestModel {

    @PositiveOrZero(message = "cannot be negative")
    private Integer leagueId;

    @PositiveOrZero(message = "cannot be negative")
    private Integer teamId;

    @Pattern(regexp = "^[a-zA-Z]{4,}$", message = "must contain only letters and be at least 4 characters long")
    private String country;
}
