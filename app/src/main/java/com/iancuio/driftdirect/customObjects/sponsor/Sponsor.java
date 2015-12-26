package com.iancuio.driftdirect.customObjects.sponsor;

import java.io.Serializable;

/**
 * Created by Soulstorm on 11/30/2015.
 */
public class Sponsor implements Serializable {
    private Long id;
    private String name;
    private String title;
    private String description;
    private String email;
    private String telephoneNr;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNr() {
        return telephoneNr;
    }

    public void setTelephoneNr(String telephoneNr) {
        this.telephoneNr = telephoneNr;
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
