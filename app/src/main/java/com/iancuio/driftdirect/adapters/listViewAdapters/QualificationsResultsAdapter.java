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
import com.iancuio.driftdirect.customObjects.round.qualifier.QualifierShort;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class QualificationsResultsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<QualifierShort> qualifierShortList;


    public QualificationsResultsAdapter (Context context, List<QualifierShort> qualifierShortList) {
        this.context = context;
        this.qualifierShortList = qualifierShortList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return qualifierShortList.size();
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
        final QualificationResultsViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new QualificationResultsViewHolder();

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
            viewHolder = (QualificationResultsViewHolder) view.getTag();
        }

        Utils.loadImage(200, 200, context, RestUrls.FILE + qualifierShortList.get(i).getDriver().getProfilePicture(), viewHolder.driversPictureImageView, new Callback() {
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

        viewHolder.driversNameTextView.setText(qualifierShortList.get(i)
                .getDriver()
                .getFirstName() + " " + qualifierShortList.get(i).getDriver().getLastName() + ((qualifierShortList.get(i).getDriver().getNick() != null) ? " `" + qualifierShortList.get(i).getDriver().getNick() + "`" : ""));
        viewHolder.driversCarModel.setText(qualifierShortList.get(i).getDriver().getDriverDetails().getModel());
        viewHolder.driversCarHP.setText(String.valueOf(qualifierShortList.get(i).getDriver().getDriverDetails().getHorsePower()));
        viewHolder.driversPointsTextView.setText(String.valueOf(qualifierShortList.get(i).getPoints()) + " POINTS");
        viewHolder.driverOrderTextView.setText(String.valueOf(i+1));

        Utils.loadImage(100, 100, context, RestUrls.FILE + qualifierShortList.get(i).getDriver().getCountry(), viewHolder.driverCountryFlag, new Callback() {
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

class QualificationResultsViewHolder {
    CircleImageView driversPictureImageView;
    TextView driversNameTextView;
    ProgressBar driversProgressBar;
    TextView driversPointsTextView;
    TextView driversCarModel;
    TextView driversCarHP;
    ImageView driverCountryFlag;
    TextView driverOrderTextView;
}
