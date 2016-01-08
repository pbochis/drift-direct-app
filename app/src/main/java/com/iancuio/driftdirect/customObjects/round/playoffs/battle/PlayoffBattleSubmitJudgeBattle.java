package com.iancuio.driftdirect.customObjects.round.playoffs.battle;

import java.io.Serializable;

/**
 * Created by Soulstorm on 1/6/2016.
 */
public class PlayoffBattleSubmitJudgeBattle implements Serializable {
    private Long roundId;
    private Long runId;
    private PlayoffBattleSubmitDriverJudgeAwards driver1;
    private PlayoffBattleSubmitDriverJudgeAwards driver2;

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public Long getRunId() {
        return runId;
    }

    public void setRunId(Long runId) {
        this.runId = runId;
    }

    public PlayoffBattleSubmitDriverJudgeAwards getDriver1() {
        return driver1;
    }

    public void setDriver1(PlayoffBattleSubmitDriverJudgeAwards driver1) {
        this.driver1 = driver1;
    }

    public PlayoffBattleSubmitDriverJudgeAwards getDriver2() {
        return driver2;
    }

    public void setDriver2(PlayoffBattleSubmitDriverJudgeAwards driver2) {
        this.driver2 = driver2;
    }
}
