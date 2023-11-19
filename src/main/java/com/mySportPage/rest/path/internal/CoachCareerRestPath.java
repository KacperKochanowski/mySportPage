package com.mySportPage.rest.path.internal;

import org.springframework.context.annotation.Profile;

import static com.mySportPage.rest.path.internal.CommonRestParams.COACH_ID;
import static com.mySportPage.rest.path.internal.CommonRestParams.COACH_NAME;

@Profile({"test", "production"})
public interface CoachCareerRestPath {

    String ROOT_PATH = "/coach-career";
    String GET_CAREER_BY_COACH_ID = "/id/{" + COACH_ID + "}";
    String GET_CAREER_BY_COACH_NAME = "/name/{" + COACH_NAME + "}";
}
