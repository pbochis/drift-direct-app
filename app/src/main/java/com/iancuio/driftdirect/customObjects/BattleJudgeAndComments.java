package com.iancuio.driftdirect.customObjects;

import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Soulstorm on 1/7/2016.
 */
public class BattleJudgeAndComments implements Serializable {
    private String judgeName;
    private String judgeType;
    private List<Comment> positiveComments;
    private List<Comment> negativeComments;

    public String getJudgeName() {
        return judgeName;
    }

    public void setJudgeName(String judgeName) {
        this.judgeName = judgeName;
    }

    public List<Comment> getPositiveComments() {
        return positiveComments;
    }

    public void setPositiveComments(List<Comment> positiveComments) {
        this.positiveComments = positiveComments;
    }

    public List<Comment> getNegativeComments() {
        return negativeComments;
    }

    public void setNegativeComments(List<Comment> negativeComments) {
        this.negativeComments = negativeComments;
    }

    public String getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(String judgeType) {
        this.judgeType = judgeType;
    }
}
