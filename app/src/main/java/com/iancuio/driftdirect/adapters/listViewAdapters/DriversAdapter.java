package com.iancuio.driftdirect.adapters.listViewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.person.PersonShort;
import com.iancuio.driftdirect.utils.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class DriversAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<PersonShort> driversList;
    

    public DriversAdapter (Context context, List<PersonShort> driversList) {
        this.context = context;
        this.driversList = driversList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return driversList.size();
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
        final DriversViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new DriversViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_drivers, viewGroup, false);
            viewHolder.driversPictureImageView = (CircleImageView) listItem.findViewById(R.id.imageView_driversListViewItem_driversPicture);
            viewHolder.driversNameTextView = (TextView) listItem.findViewById(R.id.textView_driversListViewItem_driversName);
            viewHolder.driversProgressBar = (ProgressBar) listItem.findViewById(R.id.progressBar_driversListViewItem_progressBar);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (DriversViewHolder) view.getTag();
        }


        Utils.loadNormalImage(200, 200, context, RestUrls.FILE + driversList.get(i).getProfilePicture(), viewHolder.driversPictureImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
                viewHolder.driversProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
                viewHolder.driversProgressBar.setVisibility(View.GONE);
            }
        });

        Utils.nullCheck(driversList.get(i).getFirstName(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.driversNameTextView.setText(driversList.get(i).getFirstName() + " " + driversList.get(i).getLastName());

            }

            @Override
            public void onNull() {
                viewHolder.driversNameTextView.setText("-");
            }
        });

        return listItem;
    }
}

class DriversViewHolder {
    CircleImageView driversPictureImageView;
    TextView driversNameTextView;
    ProgressBar driversProgressBar;
}
