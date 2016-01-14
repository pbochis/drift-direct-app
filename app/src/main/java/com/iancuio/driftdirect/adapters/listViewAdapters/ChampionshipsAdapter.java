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
import com.iancuio.driftdirect.utils.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {

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

            Utils.nullCheck(championshipsList.get(i).getNextRound(), new NullCheck() {
                @Override
                public void onNotNull() {
                    Utils.nullCheck(championshipsList.get(i).getRoundTimeTable(), new NullCheck() {
                        @Override
                        public void onNotNull() {
                            viewHolder.championshipDateTextView.setText(championshipsList.get(i).getRoundTimeTable());

                        }

                        @Override
                        public void onNull() {
                            viewHolder.championshipDateTextView.setText("-");
                        }
                    });

                    Utils.nullCheck(championshipsList.get(i).getNextRound().getOrder(), new NullCheck() {
                        @Override
                        public void onNotNull() {
                            viewHolder.championshipRoundNumberTextView.setText(String.valueOf("Round " + championshipsList.get(i).getNextRound().getOrder()));

                        }

                        @Override
                        public void onNull() {
                            viewHolder.championshipRoundNumberTextView.setText("-");
                        }
                    });

                    Utils.nullCheck(championshipsList.get(i).getNextRound().getName(), new NullCheck() {
                        @Override
                        public void onNotNull() {
                            viewHolder.championshipRoundNameTextView.setText(championshipsList.get(i).getNextRound().getName());

                        }

                        @Override
                        public void onNull() {
                            viewHolder.championshipRoundNameTextView.setText("-");
                        }
                    });
                }

                @Override
                public void onNull() {
                    viewHolder.championshipRoundNumberTextView.setText("ENDED");

                }
            });

        Utils.loadNormalImage(600, 600, context, RestUrls.FILE + championshipsList.get(i).getBackgroundImage(), viewHolder.championshipBackgroundImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
                //viewHolder.championshipImageProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
            }
        });

        Utils.loadNormalImage(600, 600, context, RestUrls.FILE + championshipsList.get(i).getLogo(), viewHolder.championshipLogoImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
                viewHolder.championshipImageProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
                viewHolder.championshipImageProgressBar.setVisibility(View.GONE);
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
