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
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class ChampionshipsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<ChampionshipShort> championshipsList;

    public ChampionshipsAdapter (Context context, List<ChampionshipShort> championshipsList) {
        this.context = context;
        this.championshipsList = championshipsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return championshipsList.size();
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
        final ChampionshipsViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new ChampionshipsViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_championships, viewGroup, false);
            viewHolder.championshipInfoContainer = (LinearLayout) listItem.findViewById(R.id.linearLayout_championshipsListViewItem_infoContainer);
            viewHolder.championshipDateTextView = (TextView) listItem.findViewById(R.id.textView_championshipsListViewItem_championshipDate);
            viewHolder.championshipRoundNameTextView = (TextView) listItem.findViewById(R.id.textView_championshipsListViewItem_championshipRoundName);
            viewHolder.championshipRoundNumberTextView = (TextView) listItem.findViewById(R.id.textView_championshipsListViewItem_championshipRoundNumber);
            viewHolder.championshipLogoImageView = (ImageView) listItem.findViewById(R.id.imageView_championshipsListViewItem_championshipLogo);
            viewHolder.championshipBackgroundImageView = (ImageView) listItem.findViewById(R.id.imageView_championshipsListViewItem_backgroundImage);
            viewHolder.championshipImageProgressBar = (ProgressBar) listItem.findViewById(R.id.progressBar_championshipsListViewItem_progressBar);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (ChampionshipsViewHolder) view.getTag();
        }

        if (championshipsList.get(i).getNextRound() != null) {
            viewHolder.championshipDateTextView.setText(championshipsList.get(i).getRoundTimeTable());
            viewHolder.championshipRoundNumberTextView.setText(String.valueOf("Round " + championshipsList.get(i).getNextRound().getOrder()));
            viewHolder.championshipRoundNameTextView.setText(championshipsList.get(i).getNextRound().getName());
        } else {
            viewHolder.championshipRoundNumberTextView.setText("ENDED");
        }



        Picasso.with(context).load(RestUrls.FILE + championshipsList.get(i).getBackgroundImage()).noPlaceholder().into(viewHolder.championshipBackgroundImageView, new Callback() {
            @Override
            public void onSuccess() {

                Log.e("succes", "image succes");
                viewHolder.championshipImageProgressBar.setVisibility(View.GONE);
            }


            @Override
            public void onError() {
                Log.e("error", "imageError");

            }
        });

        Picasso.with(context).load(RestUrls.FILE + championshipsList.get(i).getLogo()).noPlaceholder().into(viewHolder.championshipLogoImageView, new Callback() {
            @Override
            public void onSuccess() {

                Log.e("succes", "image succes");
            }


            @Override
            public void onError() {
                Log.e("error", "imageError");

            }
        });

        //viewHolder.championshipInfoContainer.getLayoutParams().height = (int) ((context.getResources().getDisplayMetrics().heightPixels - Utils.getStatusBarHeight(context)) * 0.05);

        return listItem;
    }
}

class ChampionshipsViewHolder {
    LinearLayout championshipInfoContainer;
    TextView championshipDateTextView;
    TextView championshipRoundNameTextView;
    TextView championshipRoundNumberTextView;
    ImageView championshipLogoImageView;
    ImageView championshipBackgroundImageView;
    ProgressBar championshipImageProgressBar;

}
