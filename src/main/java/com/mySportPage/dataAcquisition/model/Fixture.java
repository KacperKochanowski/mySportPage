package com.mySportPage.dataAcquisition.model;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class Fixture {

    private Integer leagueId;

    private Integer season;

    private Integer id;

    private Referee referee;

    private Date start;

    private Integer stadiumId;

    private String event;

    private Team host;

    private Team guest;

    private boolean status;

    private Map<String, Integer> halftimeScore;

    private Map<String, Integer> fulltimeScore;

    private String winner;

    private Integer round;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setEvent(String hostName, String guestName) {
        this.event = String.format("%s-%s",hostName, guestName);
    }

    public void setRound(String round) {
        this.round = Integer.valueOf(round.replaceAll("\\D+", ""));
    }
}
