package com.mySportPage.task.core;

import org.springframework.stereotype.Component;

@Component
public class TaskList {

    public static final TaskDetails LEAGUE_COVERAGE_TASK = new TaskDetails(
            "LeagueCoverageTask",
            "1 hour",
            "2 seconds");

    public static final TaskDetails FIXTURE_UPDATER_TASK = new TaskDetails(
            "FixtureUpdaterTask",
            "12 hours",
            "4 seconds");

    public static final TaskDetails MISSING_RESULTING_TASK = new TaskDetails(
            "MissingResultingTask",
            "1 hour",
            "6 seconds");

    public static final TaskDetails COACH_TASK = new TaskDetails(
            "CoachTask",
            "2 hours",
            "8 seconds");

    public static final TaskDetails POSTPONED_FIXTURE_TASK = new TaskDetails(
            "PostponedFixturesTask",
            "12 hours",
            "10 seconds");
}
