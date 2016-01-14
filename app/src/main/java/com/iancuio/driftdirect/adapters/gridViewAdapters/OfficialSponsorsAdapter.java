package com.iancuio.driftdirect.adapters.gridViewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.sponsor.Sponsor;
import com.iancuio.driftdirect.utils.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import java.util.List;

/**
 * Created by Soulstorm on 12/6/2015.
 */
public class OfficialSponsorsAdapter extends BaseAdapter {

    Context context;
    List<Sponsor> sponsorList;
    LayoutInflater inflater;


    public OfficialSponsorsAdapter(Context c, List<Sponsor> sponsorList) {
        context = c;
        this.sponsorList = sponsorList;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return sponsorList.size();
    }

    @Override
    public Object getItem(int position) {
        return sponsorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return sponsorList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridItem = convertView;
        final NewsViewHolder newsViewHolder;

        if (gridItem == null) {
            newsViewHolder = new NewsViewHolder();
            gridItem = inflater.inflate(R.layout.gridview_item_news, parent, false);
            newsViewHolder.newsImage = (ImageView) gridItem.findViewById(R.id.imageView_gridViewItemNews_newsImage);
            newsViewHolder.newsTitle = (TextView) gridItem.findViewById(R.id.textView_gridViewItemNews_newsTitle);
            newsViewHolder.newsDescription = (TextView) gridItem.findViewById(R.id.textView_gridViewItemNews_newsDescription);
            newsViewHolder.newsProgressBar = (ProgressBar) gridItem.findViewById(R.id.progressBar_gridViewItemNews_progressBar);
            gridItem.setTag(newsViewHolder);
        } else {
            newsViewHolder = (NewsViewHolder) convertView.getTag();
        }

        Utils.loadNormalImage(400, 400, context, RestUrls.FILE + sponsorList.get(position).getLogo(), newsViewHolder.newsImage, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
                newsViewHolder.newsProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
                newsViewHolder.newsProgressBar.setVisibility(View.GONE);
            }
        });

        Utils.nullCheck(sponsorList.get(position).getName(), new NullCheck() {
            @Override
            public void onNotNull() {
                newsViewHolder.newsTitle.setText(sponsorList.get(position).getName());

            }

            @Override
            public void onNull() {
                newsViewHolder.newsTitle.setText("-");
            }
        });

        Utils.nullCheck(sponsorList.get(position).getDescription(), new NullCheck() {
            @Override
            public void onNotNull() {
                newsViewHolder.newsDescription.setText(sponsorList.get(position).getDescription());

            }

            @Override
            public void onNull() {
                newsViewHolder.newsDescription.setText("-");
            }
        });

        return gridItem;
    }

    class NewsViewHolder {
        ImageView newsImage;
        TextView newsTitle;
        TextView newsDescription;
        ProgressBar newsProgressBar;
    }
}
