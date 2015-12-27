package com.iancuio.driftdirect.customObjects.round.qualifier.run;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 12/26/2015.
 */
public class Run implements Serializable {
    private Long id;
    private float entrySpeed;
    private int totalPoints;
    private List<RunJudging> judgings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getEntrySpeed() {
        return entrySpeed;
    }

    public void setEntrySpeed(float entrySpeed) {
        this.entrySpeed = entrySpeed;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public List<RunJudging> getJudgings() {
        return judgings;
    }

    public void setJudgings(List<RunJudging> judgings) {
        this.judgings = judgings;
    }
}
