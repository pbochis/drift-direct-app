package com.iancuio.driftdirect.customObjects.news;

import java.io.Serializable;

/**
 * Created by Soulstorm on 12/6/2015.
 */
public class News implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String url;
    private Long logo;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLogo() {
        return logo;
    }

    public void setLogo(Long logo) {
        this.logo = logo;
    }
}
