package com.iancuio.driftdirect.customObjects.round.qualifier;

import com.iancuio.driftdirect.customObjects.person.PersonShort;

import java.io.Serializable;

/**
 * Created by Soulstorm on 12/22/2015.
 */
public class QualifierShort implements Serializable, Comparable<QualifierShort> {
    private long id;
    private PersonShort driver;
    private Float points;
    private Float firstRunScore;
    private Float secondRunScore;
    private Integer runsCompleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PersonShort getDriver() {
        return driver;
    }

    public void setDriver(PersonShort driver) {
        this.driver = driver;
    }

    public Float getPoints() {
        return points;
    }

    public void setPoints(Float points) {
        this.points = points;
    }

    public Float getFirstRunScore() {
        return firstRunScore;
    }

    public void setFirstRunScore(Float firstRunScore) {
        this.firstRunScore = firstRunScore;
    }

    public Float getSecondRunScore() {
        return secondRunScore;
    }

    public void setSecondRunScore(Float secondRunScore) {
        this.secondRunScore = secondRunScore;
    }


    @Override
    public int compareTo(QualifierShort another) {
        int result = 0;
        float firstDriverBiggestScore;
        float secondDriverBiggestScore;

        if (this.firstRunScore != null && this.secondRunScore != null) {
            if (this.getFirstRunScore() > this.getSecondRunScore()) {
                firstDriverBiggestScore = this.getFirstRunScore();
            } else {
                firstDriverBiggestScore = this.getSecondRunScore();
            }
        } else {
            if (this.firstRunScore != null) {
                firstDriverBiggestScore = this.getFirstRunScore();
            } else {
                firstDriverBiggestScore = this.getSecondRunScore();
            }
        }

        if (another.firstRunScore != null && another.secondRunScore != null) {
            if (another.getFirstRunScore() > another.getSecondRunScore()) {
                secondDriverBiggestScore = another.getFirstRunScore();
            } else {
                secondDriverBiggestScore = another.getSecondRunScore();
            }
        } else {
            if (another.getFirstRunScore() != null) {
                secondDriverBiggestScore = another.getFirstRunScore();
            } else {
                secondDriverBiggestScore = another.getSecondRunScore();
            }
        }





        if (firstDriverBiggestScore < secondDriverBiggestScore) result = 1;
        if (firstDriverBiggestScore == secondDriverBiggestScore) result = 0;
        if (firstDriverBiggestScore > secondDriverBiggestScore) result = -1;
        return result;
    }

    public Integer getRunsCompleted() {
        return runsCompleted;
    }

    public void setRunsCompleted(Integer runsCompleted) {
        this.runsCompleted = runsCompleted;
    }
}
