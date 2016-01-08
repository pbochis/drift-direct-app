package com.iancuio.driftdirect.adapters.listViewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.round.RoundShort;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class RoundsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<RoundShort> roundsList;

    public RoundsAdapter(Context context, List<RoundShort> roundsList) {
        this.context = context;
        this.roundsList = roundsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return roundsList.size();
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View listItem = view;
        final RoundsViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new RoundsViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_rounds, viewGroup, false);
            viewHolder.roundsImageImageView = (CircleImageView) listItem.findViewById(R.id.imageView_roundsListViewItem_roundImage);
            viewHolder.roundsNumberTextView = (TextView) listItem.findViewById(R.id.textView_roundsListViewItem_roundNumber);
            viewHolder.roundsStatusTextView = (TextView) listItem.findViewById(R.id.textView_roundsListViewItem_roundStatus);
            viewHolder.roundsNameTextView = (TextView) listItem.findViewById(R.id.textView_roundsListViewItem_roundName);
            viewHolder.roundsDateTextView = (TextView) listItem.findViewById(R.id.textView_roundsListViewItem_roundDate);
            viewHolder.roundsBackground = (RelativeLayout) listItem.findViewById(R.id.relativeLayout_roundsListViewItem_roundBackground);
            viewHolder.roundsPictureProgressBar = (ProgressBar) listItem.findViewById(R.id.progressBar_roundsListViewItem_progressBar);

            listItem.setTag(viewHolder);
        } else {
            viewHolder = (RoundsViewHolder) view.getTag();
        }
        viewHolder.roundsNumberTextView.setText("Round " + String.valueOf(i+1));

        if (roundsList.get(i).getRoundStatus().name().equals("ENDED")) {
            viewHolder.roundsBackground.setBackgroundColor(context.getResources().getColor(R.color.menu_past_event_color));
        } else if (roundsList.get(i).getRoundStatus().name().equals("ONGOING")) {
            viewHolder.roundsBackground.setBackgroundColor(context.getResources().getColor(R.color.menu_next_event_color));
        } else if (roundsList.get(i).getRoundStatus().name().equals("NEXT")) {
            viewHolder.roundsBackground.setBackgroundColor(context.getResources().getColor(R.color.menu_next_event_color));
        }
            viewHolder.roundsStatusTextView.setText(roundsList.get(i).getRoundStatus().name());
        viewHolder.roundsNameTextView.setText(roundsList.get(i).getName());
        viewHolder.roundsDateTextView.setText(roundsList.get(i).getRoundTimeTable());

        Utils.loadImage(600, 600, context, RestUrls.FILE + roundsList.get(i).getLogo(), viewHolder.roundsImageImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
                viewHolder.roundsPictureProgressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
            }
        });

        return listItem;
    }
}



class RoundsViewHolder {
    CircleImageView roundsImageImageView;
    TextView roundsNumberTextView;
    TextView roundsStatusTextView;
    TextView roundsNameTextView;
    TextView roundsDateTextView;
    RelativeLayout roundsBackground;
    ProgressBar roundsPictureProgressBar;
}
