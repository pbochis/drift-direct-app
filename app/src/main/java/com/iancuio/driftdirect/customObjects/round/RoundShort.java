package com.iancuio.driftdirect.customObjects.round;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.joda.time.JodaTimePermission;
import org.joda.time.*;

import java.io.Serializable;

/**
 * Created by Soulstorm on 11/29/2015.
 *
 */

public class RoundShort implements Serializable {

    private Long id;
    private int order;
    private String name;
    private Long logo;
    private DateTime startDate;
    private DateTime endDate;
    private RoundStatus roundStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public RoundStatus getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(RoundStatus roundStatus) {
        this.roundStatus = roundStatus;
    }

    public String getRoundTimeTable() {
        return getStartDate().getDayOfMonth() + " - " + getEndDate().getDayOfMonth() + " " + getStartDate().monthOfYear().getAsText() + " " + getStartDate().year().getAsText();

    }
}


