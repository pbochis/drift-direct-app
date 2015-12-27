package com.iancuio.driftdirect.customObjects.round.qualifier.run;

import com.iancuio.driftdirect.customObjects.championship.judge.ChampionshipJudgeParticipation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 12/26/2015.
 */
public class RunJudging implements Serializable {
    private Long id;
    private ChampionshipJudgeParticipation judgeParticipation;
    private List<AwardedPoints> awardedPoints;
    private List<Comment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChampionshipJudgeParticipation getJudgeParticipation() {
        return judgeParticipation;
    }

    public void setJudgeParticipation(ChampionshipJudgeParticipation judgeParticipation) {
        this.judgeParticipation = judgeParticipation;
    }

    public List<AwardedPoints> getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(List<AwardedPoints> awardedPoints) {
        this.awardedPoints = awardedPoints;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
