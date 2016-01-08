package com.iancuio.driftdirect.customObjects.round;

import com.iancuio.driftdirect.customObjects.round.qualifier.QualifierShort;
import com.iancuio.driftdirect.customObjects.track.Track;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 11/19/2015.
 */
public class Round implements Serializable {
    private Long id;
    private String name;
    private DateTime startDate;
    private DateTime endDate;
    String ticketsUrl;
    String liveStream;
    private Track track;
    private List<RoundScheduleEntry> schedule;
    private List<QualifierShort> qualifiers;
    private QualifierShort currentDriver;

    public List<RoundScheduleEntry> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<RoundScheduleEntry> schedule) {
        this.schedule = schedule;
    }

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

    public String getTicketsUrl() {
        return ticketsUrl;
    }

    public void setTicketsUrl(String ticketsUrl) {
        this.ticketsUrl = ticketsUrl;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public String getLiveStream() {
        return liveStream;
    }

    public void setLiveStream(String liveStream) {
        this.liveStream = liveStream;
    }

    public List<QualifierShort> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<QualifierShort> qualifiers) {
        this.qualifiers = qualifiers;
    }

    public QualifierShort getCurrentDriver() {
        return currentDriver;
    }

    public void setCurrentDriver(QualifierShort currentDriver) {
        this.currentDriver = currentDriver;
    }
}
