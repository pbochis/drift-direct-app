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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;
import com.iancuio.driftdirect.customObjects.round.RoundScheduleEntry;
import com.iancuio.driftdirect.utils.RestUrls;
import com.iancuio.driftdirect.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.List;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class EventScheduleAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<RoundScheduleEntry> roundScheduleEntryList;

    public EventScheduleAdapter (Context context, List<RoundScheduleEntry> roundScheduleEntryList) {
        this.context = context;
        this.roundScheduleEntryList = roundScheduleEntryList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return roundScheduleEntryList.size();
    }

    @Override
    public Object getItem(int i) {
        return roundScheduleEntryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public boolean eventIsNow(DateTime startHour, DateTime endHour) {
        return new Interval( startHour, endHour).contains(DateTime.now());
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View listItem = view;
        final EventScheduleViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new EventScheduleViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_schedule, viewGroup, false);
            viewHolder.scheduleNameTextView = (TextView) listItem.findViewById(R.id.textView_scheduleListViewItem_name);
            viewHolder.scheduleDateTextView = (TextView) listItem.findViewById(R.id.textView_scheduleListViewItem_date);
            viewHolder.scheduleTimeTextView = (TextView) listItem.findViewById(R.id.textView_scheduleListViewItem_time);
            viewHolder.scheduleBubbleTextView = (ImageView) listItem.findViewById(R.id.imageView_scheduleListViewItem_stateBubble);
            viewHolder.scheduleStateLineImageView = (ImageView) listItem.findViewById(R.id.imageView_scheduleListViewItem_stateLine);
            listItem.setTag(viewHolder);
        } else {
            viewHolder = (EventScheduleViewHolder) view.getTag();
        }

        viewHolder.scheduleNameTextView.setText(roundScheduleEntryList.get(i).getName());
        viewHolder.scheduleDateTextView.setText(String.valueOf(roundScheduleEntryList.get(i).getStartDate().getDayOfMonth() + " " + roundScheduleEntryList.get(i).getStartDate().monthOfYear().getAsText() + " "  + roundScheduleEntryList.get(i).getStartDate().year().get()));
        viewHolder.scheduleTimeTextView.setText(String.valueOf(roundScheduleEntryList.get(i).getStartDate().hourOfDay().get() + " - " + roundScheduleEntryList.get(i).getEndDate().hourOfDay().get()));

        if (eventIsNow(roundScheduleEntryList.get(i).getStartDate(), roundScheduleEntryList.get(i).getEndDate())) {
            viewHolder.scheduleBubbleTextView.setBackgroundResource(R.drawable.schedule_current);
        } else {
            if (roundScheduleEntryList.get(i).getStartDate().isBeforeNow()) {
                viewHolder.scheduleBubbleTextView.setBackgroundResource(R.drawable.schedule_past);
            } else {
                if (!eventIsNow(roundScheduleEntryList.get(i).getStartDate(), roundScheduleEntryList.get(i).getEndDate())) {
                    viewHolder.scheduleBubbleTextView.setBackgroundResource(R.drawable.schedule_next);
                }
            }
        }

        if (i+1 == roundScheduleEntryList.size()) {
            viewHolder.scheduleStateLineImageView.setVisibility(View.GONE);
        }

        return listItem;
    }
}

class EventScheduleViewHolder {
    TextView scheduleNameTextView;
    TextView scheduleDateTextView;
    TextView scheduleTimeTextView;
    ImageView scheduleBubbleTextView;
    ImageView scheduleStateLineImageView;

}
