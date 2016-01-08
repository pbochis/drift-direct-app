package com.iancuio.driftdirect.adapters.listViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.round.RoundScheduleEntry;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Interval;

import java.util.List;

public class BattleRunBubbleAdapter extends RecyclerView.Adapter<BattleRunBubbleViewHolder> {

    int currentRunNumber;
    int runTotalSize;
    boolean switchVisibility;
    Integer currentVisiblePosition;

    public BattleRunBubbleAdapter(int runTotalSize, int currentRunNumber, boolean switchVisibility, Integer currentVisiblePosition) {
        this.currentRunNumber = currentRunNumber;
        this.runTotalSize = runTotalSize;
        this.switchVisibility = switchVisibility;
        this.currentVisiblePosition = currentVisiblePosition;
    }

    @Override
    public BattleRunBubbleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.listview_row_battle_run, parent, false);

        return new BattleRunBubbleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BattleRunBubbleViewHolder holder, int position) {

        if (!switchVisibility) {
            if (position + 1 < currentRunNumber) {
                holder.currentBubbleFrameLayout.setVisibility(View.GONE);
                holder.nextBubbleFrameLayout.setVisibility(View.GONE);
            } else if (position + 1 == currentRunNumber) {
                holder.endedBubbleFrameLayout.setVisibility(View.GONE);
                holder.nextBubbleFrameLayout.setVisibility(View.GONE);
            } else if (position + 1 > currentRunNumber) {
                holder.endedBubbleFrameLayout.setVisibility(View.GONE);
                holder.currentBubbleFrameLayout.setVisibility(View.GONE);
            }
        } else {
            if (currentVisiblePosition != null) {
                if (currentVisiblePosition != position) {
                    holder.currentBubbleFrameLayout.setVisibility(View.GONE);
                    holder.nextBubbleFrameLayout.setVisibility(View.GONE);
                    holder.endedBubbleFrameLayout.setVisibility(View.VISIBLE);
                } else {
                    holder.currentBubbleFrameLayout.setVisibility(View.VISIBLE);
                    holder.nextBubbleFrameLayout.setVisibility(View.GONE);
                    holder.endedBubbleFrameLayout.setVisibility(View.GONE);
                }
            }
        }

            if (((position + 1) % 2) == 0) {
                holder.lineConnectorLinearLayout.setVisibility(View.INVISIBLE);
            } else {
                holder.lineConnectorLinearLayout.setVisibility(View.VISIBLE);
            }

            int displayRoundNumber = position + 1;
            holder.runNumberTextView.setText("RUN\n" + displayRoundNumber);
    }

    @Override
    public int getItemCount() {
        return runTotalSize;
    }
}

class BattleRunBubbleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    FrameLayout endedBubbleFrameLayout;
    FrameLayout currentBubbleFrameLayout;
    FrameLayout nextBubbleFrameLayout;
    TextView runNumberTextView;
    LinearLayout lineConnectorLinearLayout;

    public BattleRunBubbleViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        endedBubbleFrameLayout = (FrameLayout) itemView.findViewById(R.id.frameLayout_battleRunBubbleListViewRow_endedBubble);
        currentBubbleFrameLayout = (FrameLayout) itemView.findViewById(R.id.frameLayout_battleRunBubbleListViewRow_currentBubble);
        nextBubbleFrameLayout = (FrameLayout) itemView.findViewById(R.id.frameLayout_battleRunBubbleListViewRow_nextBubble);
        runNumberTextView = (TextView) itemView.findViewById(R.id.textView_battleRunBubbleListViewRow_runNumber);
        lineConnectorLinearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_battleRunBubbleListViewRow_layoutConnector);
    }

    @Override
    public void onClick(View v) {

    }
}