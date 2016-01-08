package com.iancuio.driftdirect.customObjects.championship.driver;

import java.io.Serializable;

/**
 * Created by Soulstorm on 12/1/2015.
 */
public class ChampionshipDriverParticipationResults implements Serializable {
    private Long id;
    private Integer rank;
    private Integer totalPoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }
}
