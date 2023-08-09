package com.mySportPage.mapper.mapStruct;


import com.mySportPage.model.Team;
import com.mySportPage.model.dto.TeamDTO;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface TeamMapper {

    TeamDTO mapToDTO(Team team);
    List<TeamDTO> mapToDTO(List<Team> teams);
}
