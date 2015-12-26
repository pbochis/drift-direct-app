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
import com.iancuio.driftdirect.customObjects.person.PersonShort;
import com.iancuio.driftdirect.utils.RestUrls;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class Top16Top32Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<PersonShort> driversList;


    public Top16Top32Adapter (Context context, List<PersonShort> driversList) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        View listItem = view;
        final Top16Top32ViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new Top16Top32ViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_top16top32, viewGroup, false);
            viewHolder.firstDriverFlag = (ImageView) listItem.findViewById(R.id.imageView_top16Top32Layout_firstDriverFlag);
            viewHolder.firstDriverName = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_firstDriverName);
            viewHolder.firstDriverPicture = (CircleImageView) listItem.findViewById(R.id.imageView_top16Yop32Layout_firstBadgePicture);
            viewHolder.firstDriverOrder = (TextView) listItem.findViewById(R.id.textView_top16Yop32Layout_firstBadgeOrder);
            viewHolder.firstDriverStatus = (TextView) listItem.findViewById(R.id.textView_top16Yop32Layout_firstBadgeText);
            viewHolder.firstDriverCarModel = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_firstDriverCarModel);
            viewHolder.firstDriverCarHP = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_firstDriverCarHP);

            viewHolder.secondDriverFlag = (ImageView) listItem.findViewById(R.id.imageView_top16Top32Layout_firstDriverFlag);
            viewHolder.secondDriverName = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_firstDriverName);
            viewHolder.secondDriverPicture = (CircleImageView) listItem.findViewById(R.id.imageView_top16Yop32Layout_secondBadgePicture);
            viewHolder.secondDriverOrder = (TextView) listItem.findViewById(R.id.textView_top16Yop32Layout_secondBadgeOrder);
            viewHolder.secondDriverStatus = (TextView) listItem.findViewById(R.id.textView_top16Yop32Layout_secondBadgeText);
            viewHolder.secondDriverCarModel = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_secondDriverCarModel);
            viewHolder.secondDriverCarHP = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_secondDriverCarHP);

            listItem.setTag(viewHolder);
        } else {
            viewHolder = (Top16Top32ViewHolder) view.getTag();
        }




        return listItem;
    }
}

class Top16Top32ViewHolder {
    ImageView firstDriverFlag;
    TextView firstDriverName;
    CircleImageView firstDriverPicture;
    TextView firstDriverOrder;
    TextView firstDriverStatus;
    TextView firstDriverCarModel;
    TextView firstDriverCarHP;

    ImageView secondDriverFlag;
    TextView secondDriverName;
    CircleImageView secondDriverPicture;
    TextView secondDriverOrder;
    TextView secondDriverStatus;
    TextView secondDriverCarModel;
    TextView secondDriverCarHP;

}
