package com.mySportPage.mapper.mapStruct;

import com.mySportPage.model.CoachCareer;
import com.mySportPage.model.Team;
import org.mapstruct.Mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface CoachCareerMapper {

    default List<CoachCareer> mapToCoachCareerList(List<Object[]> data) {
        return data.stream()
                .map(this::mapToCoachCareerMapperModel)
                .collect(Collectors.toList());
    }

    default CoachCareer mapToCoachCareerMapperModel(Object[] value) {
        if (value == null || value.length < 4) {
            return null;
        }

        CoachCareer model = new CoachCareer();
        model.setTeam(new Team((Integer) value[0], ((String) value[1])));
        model.setStart((Date) value[2]);
        model.setEnd((Date) value[3]);

        return model;
    }
}
