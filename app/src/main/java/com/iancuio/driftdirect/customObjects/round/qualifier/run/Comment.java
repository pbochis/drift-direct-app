package com.iancuio.driftdirect.customObjects.round.qualifier.run;

import java.io.Serializable;

/**
 * Created by Soulstorm on 12/26/2015.
 */
public class Comment implements Serializable {
    private Long id;
    private String comment;
    private Boolean positive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getPositive() {
        return positive;
    }

    public void setPositive(Boolean positive) {
        this.positive = positive;
    }
}
