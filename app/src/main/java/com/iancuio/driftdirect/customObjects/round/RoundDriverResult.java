package com.iancuio.driftdirect.customObjects.round;

import com.iancuio.driftdirect.customObjects.person.PersonShort;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Soulstorm on 1/7/2016.
 */
public class RoundDriverResult implements Serializable, Comparable<RoundDriverResult> {
    private Long id;
    private PersonShort driver;
    private float qualifierPoints;
    private int qualifierRanking;
    private int playoffRanking;
    private float playoffPoints;
    private float roundScore;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonShort getDriver() {
        return driver;
    }

    public void setDriver(PersonShort driver) {
        this.driver = driver;
    }

    public float getQualifierPoints() {
        return qualifierPoints;
    }

    public void setQualifierPoints(float qualifierPoints) {
        this.qualifierPoints = qualifierPoints;
    }

    public int getQualifierRanking() {
        return qualifierRanking;
    }

    public void setQualifierRanking(int qualifierRanking) {
        this.qualifierRanking = qualifierRanking;
    }

    public int getPlayoffRanking() {
        return playoffRanking;
    }

    public void setPlayoffRanking(int playoffRanking) {
        this.playoffRanking = playoffRanking;
    }

    public float getPlayoffPoints() {
        return playoffPoints;
    }

    public void setPlayoffPoints(float playoffPoints) {
        this.playoffPoints = playoffPoints;
    }

    public float getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(float roundScore) {
        this.roundScore = roundScore;
    }

    @Override
    public int compareTo(RoundDriverResult another) {
        int result = 0;
        if (this.getRoundScore() < another.getRoundScore()) result = 1;
        if (this.getRoundScore() == another.getRoundScore()) result = 0;
        if (this.getRoundScore() > another.getRoundScore()) result = -1;
        return result;
    }
}
