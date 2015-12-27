package com.iancuio.driftdirect.adapters.listViewAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.judge.JudgePointsAllocation;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Comment;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.Run;
import com.iancuio.driftdirect.customObjects.temporary.NormalDriver;
import com.iancuio.driftdirect.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class PublicRunDetailsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private Run run;


    public PublicRunDetailsAdapter(Context context, Run run) {
        this.context = context;
        this.run = run;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return run.getJudgings().size();
    }

    @Override
    public Object getItem(int i) {
        return run.getJudgings().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View listItem = view;
        final RunDetailsViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new RunDetailsViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_run_details, viewGroup, false);
            viewHolder.judgeTypeTextView = (TextView) listItem.findViewById(R.id.textView_runDetailsListViewRow_judgeType);
            viewHolder.judgeNameTextView = (TextView) listItem.findViewById(R.id.textView_runDetailsListViewRow_judgeName);
            viewHolder.pointsListView = (ListView) listItem.findViewById(R.id.listView_runDetailsListViewRow_pointsAllocationList);
            viewHolder.positiveCommentsListView = (ListView) listItem.findViewById(R.id.listView_runDetailsListViewRow_positiveComments);
            viewHolder.negativeCommentsListView = (ListView) listItem.findViewById(R.id.listView_runDetailsListViewRow_negativeComments);

            listItem.setTag(viewHolder);
        } else {
            viewHolder = (RunDetailsViewHolder) view.getTag();
        }

        List<Comment> positiveCommentsList = new ArrayList<>();
        List<Comment> negativeCommentsList = new ArrayList<>();

        for (Comment comment:run.getJudgings().get(i).getComments()) {
            if (comment.getPositive()) {
                positiveCommentsList.add(comment);
            } else {
                negativeCommentsList.add(comment);
            }
        }

        viewHolder.judgeTypeTextView.setText(run.getJudgings().get(i).getJudgeParticipation().getJudgeType());
        viewHolder.judgeNameTextView.setText(run.getJudgings().get(i).getJudgeParticipation().getJudge().getFirstName() + " " + run.getJudgings().get(i).getJudgeParticipation().getJudge().getLastName());

        viewHolder.pointsListView.setAdapter(new PublicJudgePointsAllocationAdapter(context, run.getJudgings().get(i).getAwardedPoints()));
        Utils.setListViewHeightBasedOnItems(viewHolder.pointsListView);

        viewHolder.positiveCommentsListView.setAdapter(new RunJudgeCommentsAdapter(context, positiveCommentsList));
        Utils.setListViewHeightBasedOnItems(viewHolder.positiveCommentsListView);

        viewHolder.negativeCommentsListView.setAdapter(new RunJudgeCommentsAdapter(context, negativeCommentsList));
        Utils.setListViewHeightBasedOnItems(viewHolder.negativeCommentsListView);

        return listItem;
    }

}

class RunDetailsViewHolder {
    TextView judgeTypeTextView;
    TextView judgeNameTextView;
    ListView pointsListView;
    ListView positiveCommentsListView;
    ListView negativeCommentsListView;
}
