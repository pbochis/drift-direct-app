package com.iancuio.driftdirect.customObjects.championship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iancuio.driftdirect.customObjects.round.RoundShort;

import org.joda.time.DateTime;
import org.joda.time.YearMonth;

import java.io.Serializable;

/**
 * Created by Soulstorm on 11/29/2015.
 */
public class ChampionshipShort implements Serializable {

    private Long id;
    private Long backgroundImage;
    private Long logo;
    private RoundShort nextRound;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Long backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public RoundShort getNextRound() {
        return nextRound;
    }

    public String getRoundTimeTable() {
        return getNextRound().getStartDate().getDayOfMonth() + " - " + getNextRound().getEndDate().getDayOfMonth() + "\n" + getNextRound().getStartDate().monthOfYear().getAsText() + " " + getNextRound().getStartDate().year().getAsText();

    }

    public void setNextRound(RoundShort nextRound) {
        this.nextRound = nextRound;
    }

//    public DateTime getDate() {
//        return date;
//    }
//
//    public void setDate(DateTime date) {
//        this.date = date;
//    }
}
