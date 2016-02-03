package com.iancuio.driftdirect.customObjects.driver;

import com.iancuio.driftdirect.customObjects.sponsor.Sponsor;
import com.iancuio.driftdirect.customObjects.team.Team;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 12/22/2015.
 */
public class DriverDetailsShort implements Serializable {
    private long id;
    private String make;
    private String model;
    private int horsePower;
    private Team team;
    private List<Sponsor> sponsors;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }
}
