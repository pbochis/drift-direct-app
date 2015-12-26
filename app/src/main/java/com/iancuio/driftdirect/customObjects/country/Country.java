package com.iancuio.driftdirect.customObjects.country;

import java.io.Serializable;

/**
 * Created by Soulstorm on 11/30/2015.
 */
public class Country implements Serializable {
    private Long id;
    private String name;
    private Long flag;

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

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }
}
