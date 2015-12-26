package com.iancuio.driftdirect.customObjects.track;

import java.io.Serializable;

/**
 * Created by Soulstorm on 12/2/2015.
 */
public class Track implements Serializable {
    private Long id;
    private String layout;
    private String description;
    private String videoUrl;
    private String judgingCriteria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTrackUrl() {
        return videoUrl;
    }

    public void setTrackUrl(String trackUrl) {
        this.videoUrl = trackUrl;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getJudgingCriteria() {
        return judgingCriteria;
    }

    public void setJudgingCriteria(String judgingCriteria) {
        this.judgingCriteria = judgingCriteria;
    }
}
