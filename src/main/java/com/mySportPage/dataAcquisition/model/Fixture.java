package com.mySportPage.dataAcquisition.model;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class Fixture {

    private Integer leagueId;

    private String season;

    private Integer id;

    private Referee referee;

    private Date start;

    private Integer stadiumId;

    private String event;

    private Team host;

    private Team guest;

    private boolean finished;

    private Map<String, Integer> halftimeScore;

    private Map<String, Integer> fulltimeScore;

    private String winner;

    private Integer round;

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setEvent(String hostName, String guestName) {
        this.event = String.format("%s - %s",hostName, guestName);
    }

    public void setRound(String round) {
        this.round = Integer.valueOf(round.replaceAll("\\D+", ""));
    }

    /**
     * Field season needs custom setter because most of the football seasons or at least ones used now don't start and finish at the same year,
     * so putting there only one year would be misleading.
     * e.g. Polish Ekstraklasa lasts between 2022-07-15 and 2023-05-27.
     * @param season - year when competitions start
     */

    public void setSeason(Integer season) {
        this.season = String.format("%s/%d", String.valueOf(season).substring(2), Integer.parseInt(String.valueOf(season).substring(1)) + 1);
    }
}
