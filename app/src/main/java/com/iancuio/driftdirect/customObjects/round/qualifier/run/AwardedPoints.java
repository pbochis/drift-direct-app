package com.iancuio.driftdirect.customObjects.round.qualifier.run;

import com.iancuio.driftdirect.customObjects.championship.judge.JudgePointsAllocation;

import java.io.Serializable;

/**
 * Created by Soulstorm on 12/26/2015.
 */
public class AwardedPoints implements Serializable {
    private Long id;
    private JudgePointsAllocation pointsAllocation;
    private float awardedPoints;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JudgePointsAllocation getPointsAllocation() {
        return pointsAllocation;
    }

    public void setPointsAllocation(JudgePointsAllocation pointsAllocation) {
        this.pointsAllocation = pointsAllocation;
    }

    public float getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(float awardedPoints) {
        this.awardedPoints = awardedPoints;
    }
}
