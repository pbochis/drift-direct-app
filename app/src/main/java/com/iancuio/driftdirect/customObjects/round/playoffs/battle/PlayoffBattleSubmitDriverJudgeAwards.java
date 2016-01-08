package com.iancuio.driftdirect.customObjects.round.playoffs.battle;

import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 1/6/2016.
 */
public class PlayoffBattleSubmitDriverJudgeAwards implements Serializable {
    private Long qualifiedDriverId;
    private int points;
    private List<Comment> comments;

    public Long getQualifiedDriverId() {
        return qualifiedDriverId;
    }

    public void setQualifiedDriverId(Long qualifiedDriverId) {
        this.qualifiedDriverId = qualifiedDriverId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
