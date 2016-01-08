package com.iancuio.driftdirect.customObjects.round.playoffs.battle;

import com.iancuio.driftdirect.customObjects.person.PersonShort;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 1/7/2016.
 */
public class BattleRoundRunJudgeScores implements Serializable {
    private PersonShort judge;

    private int driver1Score;
    private List<Comment> driver1Comments;

    private int driver2Score;
    private List<Comment> driver2Comments;

    public PersonShort getJudge() {
        return judge;
    }

    public void setJudge(PersonShort judge) {
        this.judge = judge;
    }

    public int getDriver1Score() {
        return driver1Score;
    }

    public void setDriver1Score(int driver1Score) {
        this.driver1Score = driver1Score;
    }

    public List<Comment> getDriver1Comments() {
        return driver1Comments;
    }

    public void setDriver1Comments(List<Comment> driver1Comments) {
        this.driver1Comments = driver1Comments;
    }

    public int getDriver2Score() {
        return driver2Score;
    }

    public void setDriver2Score(int driver2Score) {
        this.driver2Score = driver2Score;
    }

    public List<Comment> getDriver2Comments() {
        return driver2Comments;
    }

    public void setDriver2Comments(List<Comment> driver2Comments) {
        this.driver2Comments = driver2Comments;
    }
}
