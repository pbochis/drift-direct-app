package com.iancuio.driftdirect.customObjects.round.playoffs.battle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soulstorm on 1/7/2016.
 */
public class PLayoffBattleRoundJudgeScores implements Serializable {
    private List<BattleRoundRunJudgeScores> firstRunScores;
    private List<BattleRoundRunJudgeScores> secondRunScores;

    public List<BattleRoundRunJudgeScores> getFirstRunScores() {
        return firstRunScores;
    }

    public void setFirstRunScores(List<BattleRoundRunJudgeScores> firstRunScores) {
        this.firstRunScores = firstRunScores;
    }

    public List<BattleRoundRunJudgeScores> getSecondRunScores() {
        return secondRunScores;
    }

    public void setSecondRunScores(List<BattleRoundRunJudgeScores> secondRunScores) {
        this.secondRunScores = secondRunScores;
    }
}
