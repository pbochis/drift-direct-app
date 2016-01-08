package com.iancuio.driftdirect.customObjects.round.playoffs.battle;

import com.iancuio.driftdirect.customObjects.round.qualifier.QualifiedDriver;

import java.io.Serializable;

/**
 * Created by Soulstorm on 1/6/2016.
 */
public class PlayoffBattleDriver implements Serializable {
    private QualifiedDriver driver;
    private boolean lead;
    private boolean advantage;

    public QualifiedDriver getDriver() {
        return driver;
    }

    public void setDriver(QualifiedDriver driver) {
        this.driver = driver;
    }

    public boolean isLead() {
        return lead;
    }

    public void setLead(boolean lead) {
        this.lead = lead;
    }

    public boolean isAdvantage() {
        return advantage;
    }

    public void setAdvantage(boolean advantage) {
        this.advantage = advantage;
    }
}
