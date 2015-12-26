package com.iancuio.driftdirect.customObjects.championship.judge;

import com.iancuio.driftdirect.customObjects.person.PersonShort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 11/20/2015.
 */
public class ChampionshipJudgeParticipation implements Serializable {
    private Long id;
    private PersonShort judge;
    private String judgeType;
    private List<JudgePointsAllocation> pointsAllocations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonShort getJudge() {
        return judge;
    }

    public void setJudge(PersonShort judge) {
        this.judge = judge;
    }

    public String getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(String judgeType) {
        this.judgeType = judgeType;
    }

    public List<JudgePointsAllocation> getPointsAllocations() {
        return pointsAllocations;
    }

    public void setPointsAllocations(List<JudgePointsAllocation> pointsAllocations) {
        this.pointsAllocations = pointsAllocations;
    }
}
