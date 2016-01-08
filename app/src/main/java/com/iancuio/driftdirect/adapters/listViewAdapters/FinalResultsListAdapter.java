package com.iancuio.driftdirect.adapters.listViewAdapters;

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
import com.iancuio.driftdirect.customObjects.round.RoundDriverResult;
import com.iancuio.driftdirect.customObjects.round.qualifier.QualifierShort;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class FinalResultsListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<RoundDriverResult> roundDriverResultList;


    public FinalResultsListAdapter (Context context, List<RoundDriverResult> roundDriverResultList) {
        this.context = context;
        this.roundDriverResultList = roundDriverResultList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return roundDriverResultList.size();
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
        final FinalResultsViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new FinalResultsViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_qualifications_results, viewGroup, false);
            viewHolder.driversPictureImageView = (CircleImageView) listItem.findViewById(R.id.imageView_battleJudgeLayout_firstDriverBadgePicture);
            viewHolder.driversNameTextView = (TextView) listItem.findViewById(R.id.textView_qualificationResultsListViewItem_driverName);
            viewHolder.driversProgressBar = (ProgressBar) listItem.findViewById(R.id.progressBar_battleJudgeLayout_firstDriverBadgePictureProgressBar);
            viewHolder.driversCarHP = (TextView) listItem.findViewById(R.id.textView_qualificationResultsListViewItem_carHP);
            viewHolder.driversCarModel = (TextView) listItem.findViewById(R.id.textView_qualificationResultsListViewItem_carModel);
            viewHolder.driversPointsTextView = (TextView) listItem.findViewById(R.id.textView_qualificationResultsListViewItem_badgePoints);
            viewHolder.driverCountryFlag = (ImageView) listItem.findViewById(R.id.imageView_qualificationResultsListViewItem_flag);
            viewHolder.driverOrderTextView = (TextView) listItem.findViewById(R.id.textView_qualificationResultsListViewItem_badgeOrder);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (FinalResultsViewHolder) view.getTag();
        }

        Utils.loadImage(200, 200, context, RestUrls.FILE + roundDriverResultList.get(i).getDriver().getProfilePicture(), viewHolder.driversPictureImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
                viewHolder.driversProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
            }
        });

        viewHolder.driversNameTextView.setText(roundDriverResultList.get(i)
                .getDriver()
                .getFirstName() + " " + roundDriverResultList.get(i).getDriver().getLastName() + ((roundDriverResultList.get(i).getDriver().getNick() != null) ? " `" + roundDriverResultList.get(i).getDriver().getNick() + "`" : ""));
        viewHolder.driversCarModel.setText(roundDriverResultList.get(i).getDriver().getDriverDetails().getModel());
        viewHolder.driversCarHP.setText(String.valueOf(roundDriverResultList.get(i).getDriver().getDriverDetails().getHorsePower()));
        viewHolder.driversPointsTextView.setText(String.valueOf(roundDriverResultList.get(i).getRoundScore()) + " POINTS");
        viewHolder.driverOrderTextView.setText(String.valueOf(i+1));

        Utils.loadImage(100, 100, context, RestUrls.FILE + roundDriverResultList.get(i).getDriver().getCountry(), viewHolder.driverCountryFlag, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
            }
        });

        return listItem;
    }
}

class FinalResultsViewHolder {
    CircleImageView driversPictureImageView;
    TextView driversNameTextView;
    ProgressBar driversProgressBar;
    TextView driversPointsTextView;
    TextView driversCarModel;
    TextView driversCarHP;
    ImageView driverCountryFlag;
    TextView driverOrderTextView;
}
