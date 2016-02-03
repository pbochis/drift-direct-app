package com.iancuio.driftdirect.customObjects.round.playoffs;

import com.iancuio.driftdirect.customObjects.round.qualifier.QualifiedDriver;

import java.io.Serializable;

/**
 * Created by Soulstorm on 1/5/2016.
 */
public class BattleGraphicDisplay implements Serializable {
    private long id;
    private QualifiedDriver driver1;
    private QualifiedDriver driver2;
    private QualifiedDriver winner;
    private int order;
    private Integer runsCompleted;


    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public QualifiedDriver getWinner() {
        return winner;
    }

    public void setWinner(QualifiedDriver winner) {
        this.winner = winner;
    }

    public Integer getRunsCompleted() {
        return runsCompleted;
    }

    public void setRunsCompleted(Integer runsCompleted) {
        this.runsCompleted = runsCompleted;
    }
}
