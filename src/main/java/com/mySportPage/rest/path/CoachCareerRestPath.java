package com.mySportPage.rest.path;

public interface CoachCareerRestPath {

    String ROOT_PATH = "/coach-career/";
    String GET_CAREER_BY_COACH_ID = "id/" + Params.COACH_ID;
    String GET_CAREER_BY_COACH_NAME = "name/" + Params.COACH_NAME;

    interface Params {
        String COACH_ID = "{coachId}";
        String COACH_NAME = "{coachName}";
    }
}
