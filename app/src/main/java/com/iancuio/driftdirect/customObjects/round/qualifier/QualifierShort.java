package com.iancuio.driftdirect.customObjects.round.qualifier;

import com.iancuio.driftdirect.customObjects.person.PersonShort;

import java.io.Serializable;

/**
 * Created by Soulstorm on 12/22/2015.
 */
public class QualifierShort implements Serializable, Comparable<QualifierShort> {
    private long id;
    private PersonShort driver;
    private Integer points;
    private Integer firstRunScore;
    private Integer secondRunScore;

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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getFirstRunScore() {
        return firstRunScore;
    }

    public void setFirstRunScore(Integer firstRunScore) {
        this.firstRunScore = firstRunScore;
    }

    public Integer getSecondRunScore() {
        return secondRunScore;
    }

    public void setSecondRunScore(Integer secondRunScore) {
        this.secondRunScore = secondRunScore;
    }


    @Override
    public int compareTo(QualifierShort another) {
        int result = 0;
        int firstDriverBiggestScore;
        int secondDriverBiggestScore;

        if (this.getFirstRunScore() > this.getSecondRunScore()) {
            firstDriverBiggestScore = this.getFirstRunScore();
        } else {
            firstDriverBiggestScore = this.getSecondRunScore();
        }

        if (another.getFirstRunScore() > another.getSecondRunScore()) {
            secondDriverBiggestScore = another.getFirstRunScore();
        } else {
            secondDriverBiggestScore = another.getSecondRunScore();
        }
        if (firstDriverBiggestScore < secondDriverBiggestScore) result = 1;
        if (firstDriverBiggestScore == secondDriverBiggestScore) result = 0;
        if (firstDriverBiggestScore > secondDriverBiggestScore) result = -1;
        return result;
    }
}
