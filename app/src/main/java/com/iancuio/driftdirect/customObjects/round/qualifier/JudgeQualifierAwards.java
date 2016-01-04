package com.iancuio.driftdirect.customObjects.round.qualifier;

import com.iancuio.driftdirect.customObjects.round.qualifier.run.AwardedPoints;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.JudgeAwardedPoints;

import java.util.List;

/**
 * Created by Soulstorm on 12/28/2015.
 */
public class JudgeQualifierAwards {
    private List<JudgeAwardedPoints> awardedPoints;
    private List<Comment> comments;
    private Float entrySpeed;

    public List<JudgeAwardedPoints> getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(List<JudgeAwardedPoints> awardedPoints) {
        this.awardedPoints = awardedPoints;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Float getEntrySpeed() {
        return entrySpeed;
    }

    public void setEntrySpeed(Float entrySpeed) {
        this.entrySpeed = entrySpeed;
    }
}
