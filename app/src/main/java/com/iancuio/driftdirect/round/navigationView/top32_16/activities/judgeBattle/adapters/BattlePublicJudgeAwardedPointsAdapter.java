package com.iancuio.driftdirect.round.navigationView.top32_16.activities.judgeBattle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.PublicViewBattleJudgeAwards;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.rangeSeekBar.RangeBar;
import com.iancuio.driftdirect.utils.Utils;

import java.util.List;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class BattlePublicJudgeAwardedPointsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<PublicViewBattleJudgeAwards> publicViewbattleJudgeAwardsList;

    public BattlePublicJudgeAwardedPointsAdapter (Context context, List<PublicViewBattleJudgeAwards> publicViewbattleJudgeAwardsList) {
        this.context = context;
        this.publicViewbattleJudgeAwardsList = publicViewbattleJudgeAwardsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return publicViewbattleJudgeAwardsList.size();
    }

    @Override
    public Object getItem(int i) {
        return publicViewbattleJudgeAwardsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View listItem = view;
        final BattlePublicJudgeAwardedPointsViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new BattlePublicJudgeAwardedPointsViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_judge_battle_awarded_points, viewGroup, false);
            viewHolder.judgeNamePosition1TextView = (TextView) listItem.findViewById(R.id.textView_judgeBattleAwardedPointsListViewRow_judgeNamePosition1);
            viewHolder.judgeNamePosition2TextView = (TextView) listItem.findViewById(R.id.textView_judgeBattleAwardedPointsListViewRow_judgeNamePosition2);
            viewHolder.judgeNamePosition3TextView = (TextView) listItem.findViewById(R.id.textView_judgeBattleAwardedPointsListViewRow_judgeNamePosition3);
            viewHolder.awardedPointsRangeBar = (RangeBar) listItem.findViewById(R.id.rangeBar_judgeBattleAwardedPointsListViewRow_points);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (BattlePublicJudgeAwardedPointsViewHolder) view.getTag();
        }

        Utils.nullCheck(publicViewbattleJudgeAwardsList.get(i).getFirstDriverPoints(), new NullCheck() {
            @Override
            public void onNotNull() {
                if (publicViewbattleJudgeAwardsList.get(i).getFirstDriverPoints() > 5) {
                    viewHolder.judgeNamePosition1TextView.setText(publicViewbattleJudgeAwardsList.get(i).getJudgeName());
                } else if (publicViewbattleJudgeAwardsList.get(i).getFirstDriverPoints() == 5) {
                    viewHolder.judgeNamePosition2TextView.setText(publicViewbattleJudgeAwardsList.get(i).getJudgeName());
                } else {
                    viewHolder.judgeNamePosition3TextView.setText(publicViewbattleJudgeAwardsList.get(i).getJudgeName());
                }
            }

            @Override
            public void onNull() {

            }
        });


        Utils.nullCheck(publicViewbattleJudgeAwardsList.get(i).getSecondDriverPoints(), new NullCheck() {
            @Override
            public void onNotNull() {
                if ((publicViewbattleJudgeAwardsList.get(i).getFirstDriverPoints() - publicViewbattleJudgeAwardsList.get(i).getSecondDriverPoints() > 0)) {
                    viewHolder.awardedPointsRangeBar.setThumbIndices(publicViewbattleJudgeAwardsList.get(i).getSecondDriverPoints());
                } else {
                    viewHolder.awardedPointsRangeBar.setThumbIndices(publicViewbattleJudgeAwardsList.get(i).getFirstDriverPoints());
                }
            }

            @Override
            public void onNull() {

            }
        });

        return listItem;
    }
}

class BattlePublicJudgeAwardedPointsViewHolder {
    TextView judgeNamePosition1TextView;
    TextView judgeNamePosition2TextView;
    TextView judgeNamePosition3TextView;
    RangeBar awardedPointsRangeBar;
}
