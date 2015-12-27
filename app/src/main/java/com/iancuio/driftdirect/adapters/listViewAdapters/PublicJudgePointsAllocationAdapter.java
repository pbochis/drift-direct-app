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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.judge.JudgePointsAllocation;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.AwardedPoints;
import com.iancuio.driftdirect.customObjects.temporary.NormalDriver;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class PublicJudgePointsAllocationAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<AwardedPoints> awardedPointsList;
    private int points;


    public PublicJudgePointsAllocationAdapter(Context context, List<AwardedPoints> awardedPointsList) {
        this.context = context;
        this.awardedPointsList = awardedPointsList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return awardedPointsList.size();
    }

    @Override
    public Object getItem(int i) {
        return awardedPointsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

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

        viewHolder.pointsTypeTextView.setText(awardedPointsList.get(i).getPointsAllocation().getName());
        viewHolder.maxPointsTextView.setText(String.valueOf(awardedPointsList.get(i).getPointsAllocation().getMaxPoints()));
        viewHolder.pointsSeekBar.setMax(awardedPointsList.get(i).getPointsAllocation().getMaxPoints());

        viewHolder.pointsSeekBar.setThumb(new BitmapDrawable(context.getResources(), prepareThumbBitmap(awardedPointsList.get(i).getAwardedPoints())));
        viewHolder.pointsSeekBar.setProgress(awardedPointsList.get(i).getAwardedPoints());

        viewHolder.pointsSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
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
