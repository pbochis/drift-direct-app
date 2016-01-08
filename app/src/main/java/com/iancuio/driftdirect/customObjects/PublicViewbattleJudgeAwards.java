package com.iancuio.driftdirect.customObjects;

import java.io.Serializable;

/**
 * Created by Soulstorm on 1/7/2016.
 */
public class PublicViewbattleJudgeAwards implements Serializable {
    private int firstDriverPoints;
    private int secondDriverPoints;
    private String judgeName;

    public int getFirstDriverPoints() {
        return firstDriverPoints;
    }

    public void setFirstDriverPoints(int firstDriverPoints) {
        this.firstDriverPoints = firstDriverPoints;
    }

    public int getSecondDriverPoints() {
        return secondDriverPoints;
    }

    public void setSecondDriverPoints(int secondDriverPoints) {
        this.secondDriverPoints = secondDriverPoints;
    }

    public String getJudgeName() {
        return judgeName;
    }

    public void setJudgeName(String judgeName) {
        this.judgeName = judgeName;
    }
}
