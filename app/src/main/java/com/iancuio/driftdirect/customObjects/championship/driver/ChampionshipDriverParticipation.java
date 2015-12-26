package com.iancuio.driftdirect.customObjects.championship.driver;

import com.iancuio.driftdirect.customObjects.person.Person;

import java.io.Serializable;

/**
 * Created by Soulstorm on 12/1/2015.
 */
public class ChampionshipDriverParticipation implements Serializable {
    private Long id;
    private Person driver;
    private ChampionshipDriverParticipationResults results;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getDriver() {
        return driver;
    }

    public void setDriver(Person driver) {
        this.driver = driver;
    }

    public ChampionshipDriverParticipationResults getResults() {
        return results;
    }

    public void setResults(ChampionshipDriverParticipationResults results) {
        this.results = results;
    }
}
