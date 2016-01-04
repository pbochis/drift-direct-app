package com.iancuio.driftdirect.adapters.listViewAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.judge.JudgePointsAllocation;
import com.iancuio.driftdirect.customObjects.temporary.NormalDriver;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class JudgePointsAllocationAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<JudgePointsAllocation> judgePointsAllocationList;
    private List<Integer> pointsList;
    private int points;


    public JudgePointsAllocationAdapter(Context context, List<JudgePointsAllocation> judgePointsAllocationList) {
        this.context = context;
        this.judgePointsAllocationList = judgePointsAllocationList;
        inflater = LayoutInflater.from(context);
        pointsList = new ArrayList<>();

        for (int i=0; i<judgePointsAllocationList.size(); i++) {
            pointsList.add(i, 0);
        }
    }

    @Override
    public int getCount() {
        return judgePointsAllocationList.size();
    }

    @Override
    public Object getItem(int i) {
        return judgePointsAllocationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return judgePointsAllocationList.get(i).getId();
    }

    public int getPoints(int i) {return pointsList.get(i);}

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View listItem = view;
        final PointsAllocationViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new PointsAllocationViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_judge_points_allocation, viewGroup, false);
            viewHolder.pointsTypeTextView = (TextView) listItem.findViewById(R.id.textView_judgePointsAllocationListViewRow_pointsType);
            viewHolder.minPointsTextView = (TextView) listItem.findViewById(R.id.textView_judgePointsAllocationListViewRow_minPoints);
            viewHolder.maxPointsTextView = (TextView) listItem.findViewById(R.id.textView_judgePointsAllocationListViewRow_maxPoints);
            viewHolder.pointsSeekBar = (SeekBar) listItem.findViewById(R.id.seekBar_judgePointsAllocationListViewRow_score);

            listItem.setTag(viewHolder);
        } else {
            viewHolder = (PointsAllocationViewHolder) view.getTag();
        }

        viewHolder.pointsTypeTextView.setText(judgePointsAllocationList.get(i).getName());
        viewHolder.maxPointsTextView.setText(String.valueOf(judgePointsAllocationList.get(i).getMaxPoints()));
        viewHolder.pointsSeekBar.setMax(judgePointsAllocationList.get(i).getMaxPoints());

        viewHolder.pointsSeekBar.setThumb(new BitmapDrawable(context.getResources(), prepareThumbBitmap(0)));

        viewHolder.pointsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                seekBar.setThumb(new BitmapDrawable(context.getResources(), prepareThumbBitmap(progress)));
                pointsList.add(i, seekBar.getProgress());
                //Log.e("pointsadapter", String.valueOf(pointsList.get(i)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        return listItem;

    }

    public Bitmap prepareThumbBitmap(int progress) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.score_thumb);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setTextSize(15 * context.getResources().getDisplayMetrics().density);
        paint.setColor(context.getResources().getColor(R.color.colorChampionships));
        paint.setAntiAlias(true);
        int width = (int) paint.measureText(String.valueOf(progress));
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
        canvas.drawText(String.valueOf(progress), (canvas.getWidth() - width) / 2, yPos, paint);
        return bitmap;
    }


}

class PointsAllocationViewHolder {
    TextView pointsTypeTextView;
    TextView minPointsTextView;
    TextView maxPointsTextView;
    SeekBar pointsSeekBar;
}
