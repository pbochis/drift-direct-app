package com.iancuio.driftdirect.customObjects.round;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by Soulstorm on 11/28/2015.
 */
public class RoundScheduleEntry implements Serializable {

    private long id;
    private String name;
    private DateTime startDate;
    private DateTime endDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
