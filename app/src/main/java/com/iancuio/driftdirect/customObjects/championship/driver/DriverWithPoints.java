package com.iancuio.driftdirect.customObjects.championship.driver;

import com.iancuio.driftdirect.customObjects.person.PersonShort;

import java.io.Serializable;

/**
 * Created by Soulstorm on 1/22/2016.
 */
public class DriverWithPoints implements Serializable {
    private Long id;
    private PersonShort driver;
    private Float points;

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

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }
}
