package com.mySportPage.service.impl;

import com.mySportPage.cache.CoachContainer;
import com.mySportPage.dao.CoachDao;
import com.mySportPage.model.Coach;
import com.mySportPage.model.request.CoachRequestModel;
import com.mySportPage.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoachServiceImpl implements CoachService {

    private final CoachDao dao;

    @Autowired
    public CoachServiceImpl(CoachDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Coach> getCoachesByLeague(Integer leagueId) {
        return CoachContainer.getCoaches().get(leagueId);
    }

    @Override
    public List<Coach> getCoachesByTeam(Integer teamId) {
        return CoachContainer.getCoaches().values().stream()
                .flatMap(List::stream)
                .filter(coach -> coach.getTeamId().equals(teamId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Coach> getCoachesByCountry(String country) {
        return CoachContainer.getCoaches().values().stream()
                .flatMap(List::stream)
                .filter(coach -> coach.getNationality().equals(country))
                .collect(Collectors.toList());
    }

    @Override
    public List<Coach> getCoaches(CoachRequestModel requestModel) {
        return dao.getCoaches(requestModel);
    }
}
