package com.iancuio.driftdirect.customObjects.round.playoffs;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 1/5/2016.
 */
public class PlayoffStageGraphicDisplay implements Serializable {
    private long id;
    private List<BattleGraphicDisplay> battles;
    private int order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<BattleGraphicDisplay> getBattles() {
        return battles;
    }

    public void setBattles(List<BattleGraphicDisplay> battles) {
        this.battles = battles;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
