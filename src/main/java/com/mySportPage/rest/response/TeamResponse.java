package com.mySportPage.rest.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(setterPrefix = "with")
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeamResponse extends SportPageResponse {

    private Object teams;

}
