package com.mySportPage.rest;

import com.mySportPage.controller.StadiumController;
import com.mySportPage.model.dto.StadiumDTO;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;
import static com.mySportPage.rest.path.internal.StadiumRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class StadiumRestService extends AbstractRestService {

    private final StadiumController controller;

    @Autowired
    public StadiumRestService(StadiumController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_BY_CITY)
    public SportPageResponse<List<StadiumDTO>> getByCity(
            @PathVariable(CITY) String city) {
        return processResponse(() -> controller.getByCity(city));
    }

    @GetMapping(GET_BY_ADDRESS)
    public SportPageResponse<List<StadiumDTO>> getByAddress(
            @PathVariable(ADDRESS) String address) {
        return processResponse(() -> controller.getByAddress(address));
    }

    @GetMapping(GET_BY_TEAM_ID)
    public SportPageResponse<List<StadiumDTO>> getByTeamId(
            @PathVariable(TEAM_ID) Integer teamId) {
        return processResponse(() -> controller.getByTeamId(teamId));
    }

    @GetMapping(GET_BY_TEAM_NAME)
    public SportPageResponse<List<StadiumDTO>> getByTeamName(
            @PathVariable(TEAM_NAME) String teamName) {
        return processResponse(() -> controller.getByTeamName(teamName));
    }

    @GetMapping(GET_BY_NAME)
    public SportPageResponse<List<StadiumDTO>> getByName(
            @PathVariable(NAME) String name) {
        return processResponse(() -> controller.getByName(name));
    }
}
