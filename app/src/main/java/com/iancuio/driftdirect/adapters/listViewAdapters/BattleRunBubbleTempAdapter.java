package com.iancuio.driftdirect.adapters.listViewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.person.PersonShort;
import com.iancuio.driftdirect.utils.RestUrls;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class BattleRunBubbleTempAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    int currentRunNumber;
    int runTotalSize;


    public BattleRunBubbleTempAdapter (Context context, int runTotalSize, int currentRunNumber) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.currentRunNumber = currentRunNumber;
        this.runTotalSize = runTotalSize;
    }

    @Override
    public int getCount() {
        return runTotalSize;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View listItem = view;
        final BubbleViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new BubbleViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_battle_run, viewGroup, false);
            viewHolder.endedBubbleFrameLayout = (FrameLayout) listItem.findViewById(R.id.frameLayout_battleRunBubbleListViewRow_endedBubble);
            viewHolder.currentBubbleFrameLayout = (FrameLayout) listItem.findViewById(R.id.frameLayout_battleRunBubbleListViewRow_currentBubble);
            viewHolder.nextBubbleFrameLayout = (FrameLayout) listItem.findViewById(R.id.frameLayout_battleRunBubbleListViewRow_nextBubble);
            viewHolder.runNumberTextView = (TextView) listItem.findViewById(R.id.textView_battleRunBubbleListViewRow_runNumber);
            viewHolder.lineConnectorLinearLayout = (LinearLayout) listItem.findViewById(R.id.linearLayout_battleRunBubbleListViewRow_layoutConnector);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (BubbleViewHolder) view.getTag();
        }

        if (i+1 < currentRunNumber) {
            viewHolder.currentBubbleFrameLayout.setVisibility(View.GONE);
            viewHolder.nextBubbleFrameLayout.setVisibility(View.GONE);
        } else if (i+1 == currentRunNumber) {
            viewHolder.endedBubbleFrameLayout.setVisibility(View.GONE);
            viewHolder.nextBubbleFrameLayout.setVisibility(View.GONE);
        } else if (i+1 > currentRunNumber) {
            viewHolder.endedBubbleFrameLayout.setVisibility(View.GONE);
            viewHolder.currentBubbleFrameLayout.setVisibility(View.GONE);
        }

        if(((i+1)%2)==0) {
            viewHolder.lineConnectorLinearLayout.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.lineConnectorLinearLayout.setVisibility(View.VISIBLE);
        }

        int displayRoundNumber = i + 1;
        viewHolder.runNumberTextView.setText("RUN\n" + displayRoundNumber);

        return listItem;
    }
}

class BubbleViewHolder {
    FrameLayout endedBubbleFrameLayout;
    FrameLayout currentBubbleFrameLayout;
    FrameLayout nextBubbleFrameLayout;
    TextView runNumberTextView;
    LinearLayout lineConnectorLinearLayout;
}
