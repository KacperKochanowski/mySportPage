package com.mySportPage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class FixtureStatistics {

    private Map<FixtureStatisticsEnum, String> fixtureStatistics;
}
