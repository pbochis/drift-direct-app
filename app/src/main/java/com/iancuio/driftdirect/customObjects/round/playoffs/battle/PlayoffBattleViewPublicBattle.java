package com.iancuio.driftdirect.customObjects.round.playoffs.battle;

import com.iancuio.driftdirect.customObjects.round.qualifier.QualifiedDriver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soulstorm on 1/7/2016.
 */
public class PlayoffBattleViewPublicBattle implements Serializable {
    private Long id;
    private QualifiedDriver driver1;
    private QualifiedDriver driver2;
    private QualifiedDriver winner;
    private List<PLayoffBattleRoundJudgeScores> rounds = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QualifiedDriver getDriver1() {
        return driver1;
    }

    public void setDriver1(QualifiedDriver driver1) {
        this.driver1 = driver1;
    }

    public QualifiedDriver getDriver2() {
        return driver2;
    }

    public void setDriver2(QualifiedDriver driver2) {
        this.driver2 = driver2;
    }

    public QualifiedDriver getWinner() {
        return winner;
    }

    public void setWinner(QualifiedDriver winner) {
        this.winner = winner;
    }

    public List<PLayoffBattleRoundJudgeScores> getRounds() {
        return rounds;
    }

    public void setRounds(List<PLayoffBattleRoundJudgeScores> rounds) {
        this.rounds = rounds;
    }
}

