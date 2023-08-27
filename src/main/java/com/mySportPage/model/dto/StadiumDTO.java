package com.mySportPage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
public class StadiumDTO {

    private String stadium;
    private Integer capacity;
    private String address;
    private String city;
}


