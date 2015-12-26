package com.iancuio.driftdirect.customObjects.championship;

import java.io.Serializable;

/**
 * Created by Soulstorm on 12/5/2015.
 */
public class ChampionshipRules implements Serializable {
    private Long id;
    private String rules;
    private String videoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
