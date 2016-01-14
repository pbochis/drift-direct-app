package com.iancuio.driftdirect.customObjects.round.qualifier;

import com.iancuio.driftdirect.customObjects.person.PersonShort;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Run;

import java.io.Serializable;

/**
 * Created by Soulstorm on 12/26/2015.
 */
public class Qualifier implements Serializable {

    private Long id;
    private float finalScore;
    private PersonShort driver;
    private Run firstRun;
    private Run secondRun;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(float finalScore) {
        this.finalScore = finalScore;
    }

    public PersonShort getDriver() {
        return driver;
    }

    public void setDriver(PersonShort driver) {
        this.driver = driver;
    }

    public Run getFirstRun() {
        return firstRun;
    }

    public void setFirstRun(Run firstRun) {
        this.firstRun = firstRun;
    }

    public Run getSecondRun() {
        return secondRun;
    }

    public void setSecondRun(Run secondRun) {
        this.secondRun = secondRun;
    }
}
