package com.iancuio.driftdirect.adapters.listViewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.PublicViewbattleJudgeAwards;
import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;
import com.iancuio.driftdirect.customObjects.round.RoundScheduleEntry;
import com.iancuio.driftdirect.utils.RangeSeekBar.RangeBar;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.List;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class BattlePublicJudgeAwardedPointsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<PublicViewbattleJudgeAwards> publicViewbattleJudgeAwardsList;

    public BattlePublicJudgeAwardedPointsAdapter (Context context, List<PublicViewbattleJudgeAwards> publicViewbattleJudgeAwardsList) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {

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

        if (publicViewbattleJudgeAwardsList.get(i).getFirstDriverPoints() > 5) {
            viewHolder.judgeNamePosition1TextView.setText(publicViewbattleJudgeAwardsList.get(i).getJudgeName());
        } else if (publicViewbattleJudgeAwardsList.get(i).getFirstDriverPoints() == 5) {
            viewHolder.judgeNamePosition2TextView.setText(publicViewbattleJudgeAwardsList.get(i).getJudgeName());
        } else {
            viewHolder.judgeNamePosition3TextView.setText(publicViewbattleJudgeAwardsList.get(i).getJudgeName());
        }

        if ((publicViewbattleJudgeAwardsList.get(i).getFirstDriverPoints() - publicViewbattleJudgeAwardsList.get(i).getSecondDriverPoints() > 0)) {
            viewHolder.awardedPointsRangeBar.setThumbIndices(publicViewbattleJudgeAwardsList.get(i).getSecondDriverPoints());
        } else {
            viewHolder.awardedPointsRangeBar.setThumbIndices(publicViewbattleJudgeAwardsList.get(i).getFirstDriverPoints());
        }
        return listItem;
    }
}

class BattlePublicJudgeAwardedPointsViewHolder {
    TextView judgeNamePosition1TextView;
    TextView judgeNamePosition2TextView;
    TextView judgeNamePosition3TextView;
    RangeBar awardedPointsRangeBar;
}
