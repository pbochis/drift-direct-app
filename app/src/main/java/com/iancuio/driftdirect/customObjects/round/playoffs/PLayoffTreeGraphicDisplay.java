package com.iancuio.driftdirect.customObjects.round.playoffs;

import com.iancuio.driftdirect.customObjects.round.RoundDriverResult;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 1/5/2016.
 */
public class PlayoffTreeGraphicDisplay implements Serializable {
    private long id;
    private List<PlayoffStageGraphicDisplay> stages;
    private List<RoundDriverResult> roundResults;
    private BattleGraphicDisplay currentBattle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PlayoffStageGraphicDisplay> getStages() {
        return stages;
    }

    public void setStages(List<PlayoffStageGraphicDisplay> stages) {
        this.stages = stages;
    }

    public List<RoundDriverResult> getRoundResults() {
        return roundResults;
    }

    public void setRoundResults(List<RoundDriverResult> roundResults) {
        this.roundResults = roundResults;
    }

    public BattleGraphicDisplay getCurrentBattle() {
        return currentBattle;
    }

    public void setCurrentBattle(BattleGraphicDisplay currentBattle) {
        this.currentBattle = currentBattle;
    }
}
