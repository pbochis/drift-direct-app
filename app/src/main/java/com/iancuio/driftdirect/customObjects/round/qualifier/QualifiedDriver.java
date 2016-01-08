package com.iancuio.driftdirect.customObjects.round.qualifier;

import com.iancuio.driftdirect.customObjects.person.PersonShort;

import java.io.Serializable;

/**
 * Created by Soulstorm on 1/5/2016.
 */
public class QualifiedDriver implements Serializable {
    private long id;
    private PersonShort driver;
    private int ranking;

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

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
