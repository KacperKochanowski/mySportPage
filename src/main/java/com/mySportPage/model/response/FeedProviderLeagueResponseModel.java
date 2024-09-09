package com.mySportPage.model.response;

import com.mySportPage.model.Country;
import com.mySportPage.model.League;
import com.mySportPage.model.SeasonModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedProviderLeagueResponseModel {

    private League league;
    private Country country;
    private List<SeasonModel> seasons;
}
