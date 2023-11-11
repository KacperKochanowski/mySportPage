package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coach {

    private Integer externalId;
    private Integer leagueId;
    private String name;
    private String firstName;
    private String lastName;
    private int age;
    private Date birthDate;
    private String nationality;
    private Integer height;
    private Integer weight;
    private String photo;
    private Integer teamId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return leagueId.equals(coach.leagueId) && name.equals(coach.name) && firstName.equals(coach.firstName) && lastName.equals(coach.lastName) && birthDate.equals(coach.birthDate) && nationality.equals(coach.nationality) && photo.equals(coach.photo) && teamId.equals(coach.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leagueId, name, firstName, lastName, birthDate, nationality, photo, teamId);
    }
}
