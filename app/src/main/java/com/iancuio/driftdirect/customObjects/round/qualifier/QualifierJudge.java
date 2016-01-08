package com.iancuio.driftdirect.customObjects.round.qualifier;

import android.graphics.ComposePathEffect;

import com.iancuio.driftdirect.customObjects.championship.judge.ChampionshipJudgeParticipation;
import com.iancuio.driftdirect.customObjects.person.PersonShort;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 12/28/2015.
 */
public class QualifierJudge implements Serializable {
    private Long id;
    private Long runId;
    private Long roundId;
    private int runNumber;
    private PersonShort driver;
    private ChampionshipJudgeParticipation judge;
    private List<Comment> availableComments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    public PersonShort getDriver() {
        return driver;
    }

    public void setDriver(PersonShort driver) {
        this.driver = driver;
    }

    public ChampionshipJudgeParticipation getJudge() {
        return judge;
    }

    public void setJudge(ChampionshipJudgeParticipation judge) {
        this.judge = judge;
    }

    public List<Comment> getAvailableComments() {
        return availableComments;
    }

    public void setAvailableComments(List<Comment> availableComments) {
        this.availableComments = availableComments;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }
}
