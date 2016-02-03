package com.iancuio.driftdirect.round.navigationView.eventSchedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.round.RoundScheduleEntry;
import com.iancuio.driftdirect.utils.DateTimeUtils;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.Utils;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

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

        Utils.nullCheck(roundScheduleEntryList.get(i).getName(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.scheduleNameTextView.setText(roundScheduleEntryList.get(i).getName());

            }

            @Override
            public void onNull() {
                viewHolder.scheduleNameTextView.setText("-");
            }
        });

        Utils.nullCheck(roundScheduleEntryList.get(i).getStartDate(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.scheduleDateTextView.setText(String.valueOf(roundScheduleEntryList.get(i).getStartDate().getDayOfMonth() + " " + roundScheduleEntryList.get(i).getStartDate().monthOfYear().getAsText()));

                DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm");
                String startHour = fmt.print(roundScheduleEntryList.get(i).getStartDate());
                String endHour = fmt.print(roundScheduleEntryList.get(i).getEndDate());

                viewHolder.scheduleTimeTextView.setText(String.valueOf(startHour + " - " + endHour));
            }

            @Override
            public void onNull() {
                viewHolder.scheduleDateTextView.setText("-");
                viewHolder.scheduleTimeTextView.setText("-");
            }
        });

        Utils.nullCheck(roundScheduleEntryList.get(i).getEndDate(), new NullCheck() {
            @Override
            public void onNotNull() {
                if (DateTimeUtils.eventIsNow(roundScheduleEntryList.get(i).getStartDate(), roundScheduleEntryList.get(i).getEndDate())) {
                    viewHolder.scheduleBubbleTextView.setBackgroundResource(R.drawable.schedule_current);
                } else {
                    if (roundScheduleEntryList.get(i).getStartDate().isBeforeNow()) {
                        viewHolder.scheduleBubbleTextView.setBackgroundResource(R.drawable.schedule_past);
                    } else {
                        if (!DateTimeUtils.eventIsNow(roundScheduleEntryList.get(i).getStartDate(), roundScheduleEntryList.get(i).getEndDate())) {
                            viewHolder.scheduleBubbleTextView.setBackgroundResource(R.drawable.schedule_next);
                        }
                    }
                }
            }

            @Override
            public void onNull() {

            }
        });


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
