package com.iancuio.driftdirect.round.navigationView.top32_16.activities.publicBattle.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.adapters.listViewAdapters.PointsAllocationViewHolder;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.AwardedPoints;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.Utils;

import java.util.List;

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

        Utils.nullCheck(awardedPointsList.get(i).getPointsAllocation().getName(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.pointsTypeTextView.setText(awardedPointsList.get(i).getPointsAllocation().getName());

            }

            @Override
            public void onNull() {
                viewHolder.pointsTypeTextView.setText("-");
            }
        });

        Utils.nullCheck(awardedPointsList.get(i).getPointsAllocation().getMaxPoints(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.maxPointsTextView.setText(String.valueOf(awardedPointsList.get(i).getPointsAllocation().getMaxPoints()));

                viewHolder.pointsSeekBar.setMax(awardedPointsList.get(i).getPointsAllocation().getMaxPoints()*2);

                viewHolder.pointsSeekBar.setThumb(new BitmapDrawable(context.getResources(), prepareThumbBitmap(awardedPointsList.get(i).getAwardedPoints())));
                viewHolder.pointsSeekBar.setProgress((int)awardedPointsList.get(i).getAwardedPoints()*2);

                viewHolder.pointsSeekBar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }

            @Override
            public void onNull() {
                viewHolder.maxPointsTextView.setText("-");
            }
        });



        return listItem;

    }

    public Bitmap prepareThumbBitmap(float progress) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.score_thumb);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setTextSize(13 * context.getResources().getDisplayMetrics().density);
        paint.setColor(context.getResources().getColor(R.color.colorChampionships));
        paint.setAntiAlias(true);
        int width = (int) paint.measureText(String.valueOf(progress));
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
        canvas.drawText(String.valueOf(progress), (canvas.getWidth() - width) / 2, yPos, paint);
        return bitmap;
    }


}
