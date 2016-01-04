package com.iancuio.driftdirect.customObjects.championship.judge;

import com.iancuio.driftdirect.customObjects.person.PersonShort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 11/20/2015.
 */
public class ChampionshipJudgeParticipation implements Serializable {
    private Long id;
    private JudgeType judgeType;
    private String title;
    private List<JudgePointsAllocation> pointsAllocations;
    private PersonShort judge;

    public PersonShort getJudge() {
        return judge;
    }

    public void setJudge(PersonShort judge) {
        this.judge = judge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JudgeType getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(JudgeType judgeType) {
        this.judgeType = judgeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<JudgePointsAllocation> getPointsAllocations() {
        return pointsAllocations;
    }

    public void setPointsAllocations(List<JudgePointsAllocation> pointsAllocations) {
        this.pointsAllocations = pointsAllocations;
    }
}
