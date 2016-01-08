package com.iancuio.driftdirect.adapters.gridViewAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.news.News;
import com.iancuio.driftdirect.customObjects.sponsor.Sponsor;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
    public View getView(int position, View convertView, ViewGroup parent) {
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

        Utils.loadImage(400, 400, context, RestUrls.FILE + sponsorList.get(position).getLogo(), newsViewHolder.newsImage, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
                newsViewHolder.newsProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
            }
        });

        newsViewHolder.newsTitle.setText(sponsorList.get(position).getName());
        newsViewHolder.newsDescription.setText(sponsorList.get(position).getDescription());

        return gridItem;
    }

    class NewsViewHolder {
        ImageView newsImage;
        TextView newsTitle;
        TextView newsDescription;
        ProgressBar newsProgressBar;
    }
}
