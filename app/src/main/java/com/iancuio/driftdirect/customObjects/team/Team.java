package com.iancuio.driftdirect.customObjects.team;

import com.iancuio.driftdirect.customObjects.sponsor.Sponsor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 11/30/2015.
 */
public class Team implements Serializable {
    private Long id;
    private String name;
    private List<Sponsor> sponsors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }
}
