package com.iancuio.driftdirect.round.navigationView.qualifications.adapters;

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
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class QualificationsDriversAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<QualifierShort> qualifierShortList;


    public QualificationsDriversAdapter (Context context, List<QualifierShort> qualifierShortList) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View listItem = view;
        final QualificationDriversViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new QualificationDriversViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_qualifications_driver_list, viewGroup, false);
            viewHolder.driversPictureImageView = (CircleImageView) listItem.findViewById(R.id.imageView_qualificationDriverListViewItem_badgePicture);
            viewHolder.driversNameTextView = (TextView) listItem.findViewById(R.id.textView_qualificationDriverListViewItem_driverName);
            viewHolder.driversProgressBar = (ProgressBar) listItem.findViewById(R.id.progressBar_qualificationDriverListViewItem_progressBar);
            viewHolder.driversCarHP = (TextView) listItem.findViewById(R.id.textView_qualificationDriverListViewItem_carHP);
            viewHolder.driversCarModel = (TextView) listItem.findViewById(R.id.textView_qualificationDriverListViewItem_carModel);
            viewHolder.driversOrder = (TextView) listItem.findViewById(R.id.textView_qualificationDriverListViewItem_driverOrder);
            viewHolder.driverCountryFlag = (ImageView) listItem.findViewById(R.id.imageView_qualificationDriverListViewItem_flag);

            listItem.setTag(viewHolder);
        } else {
            viewHolder = (QualificationDriversViewHolder) view.getTag();
        }

        ImageUtils.loadNormalImage(200, 200, context, RestUrls.FILE + qualifierShortList.get(i).getDriver().getProfilePicture(), viewHolder.driversPictureImageView, new Callback() {
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

        Utils.nullCheck(qualifierShortList.get(i).getDriver().getFirstName(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.driversNameTextView.setText(qualifierShortList.get(i).getDriver().getFirstName() + " " + qualifierShortList.get(i).getDriver().getLastName() + ((qualifierShortList.get(i).getDriver().getNick() != null) ? " `" + qualifierShortList.get(i).getDriver().getNick() + "`" : ""));

            }

            @Override
            public void onNull() {
                viewHolder.driversNameTextView.setText("-");
            }
        });

        Utils.nullCheck(qualifierShortList.get(i).getDriver().getDriverDetails().getModel(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.driversCarModel.setText(qualifierShortList.get(i).getDriver().getDriverDetails().getModel());

            }

            @Override
            public void onNull() {
                viewHolder.driversCarModel.setText("-");
            }
        });

        Utils.nullCheck(qualifierShortList.get(i).getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.driversCarHP.setText(String.valueOf(qualifierShortList.get(i).getDriver().getDriverDetails().getHorsePower()) + "HP");
            }

            @Override
            public void onNull() {
                viewHolder.driversCarHP.setText("-");
            }
        });
        viewHolder.driversOrder.setText(String.valueOf(i+1));

        ImageUtils.loadNormalImage(100, 100, context, RestUrls.FILE + qualifierShortList.get(i).getDriver().getCountry(), viewHolder.driverCountryFlag, new Callback() {
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

class QualificationDriversViewHolder {
    CircleImageView driversPictureImageView;
    TextView driversNameTextView;
    ProgressBar driversProgressBar;
    TextView driversCarModel;
    TextView driversCarHP;
    TextView driversOrder;
    ImageView driverCountryFlag;
}
