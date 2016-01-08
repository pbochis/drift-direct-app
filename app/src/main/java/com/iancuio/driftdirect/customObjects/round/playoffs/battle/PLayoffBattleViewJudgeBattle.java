package com.iancuio.driftdirect.customObjects.round.playoffs.battle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 1/6/2016.
 */
public class PlayoffBattleViewJudgeBattle implements Serializable {
    @JsonIgnore
    private Long id;
    private PlayoffBattleDriver driver1;
    private PlayoffBattleDriver driver2;
    private Long battleId;
    private List<Comment> availableComments;
    private Long runId;
    private Long battleRoundId;
    private int runNumber;

    public PlayoffBattleViewJudgeBattle() {
    }

    public PlayoffBattleDriver getDriver1() {
        return driver1;
    }

    public void setDriver1(PlayoffBattleDriver driver1) {
        this.driver1 = driver1;
    }

    public PlayoffBattleDriver getDriver2() {
        return driver2;
    }

    public void setDriver2(PlayoffBattleDriver driver2) {
        this.driver2 = driver2;
    }

    public Long getBattleId() {
        return battleId;
    }

    public void setBattleId(Long battleId) {
        this.battleId = battleId;
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public Long getBattleRoundId() {
        return battleRoundId;
    }

    public void setBattleRoundId(Long battleRoundId) {
        this.battleRoundId = battleRoundId;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comment> getAvailableComments() {
        return availableComments;
    }

    public void setAvailableComments(List<Comment> availableComments) {
        this.availableComments = availableComments;
    }
}
