package com.iancuio.driftdirect.round.navigationView.top32_16.activities.judgeBattle.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.judge.JudgePointsAllocation;
import com.iancuio.driftdirect.utils.interfaces.NullCheck;
import com.iancuio.driftdirect.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soulstorm on 11/14/2015.
 */
public class JudgePointsAllocationAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<JudgePointsAllocation> judgePointsAllocationList;
    private List<Float> pointsList;
    private int points;


    public JudgePointsAllocationAdapter(Context context, List<JudgePointsAllocation> judgePointsAllocationList) {
        this.context = context;
        this.judgePointsAllocationList = judgePointsAllocationList;
        inflater = LayoutInflater.from(context);
        pointsList = new ArrayList<>();

        for (int i=0; i<judgePointsAllocationList.size(); i++) {
            pointsList.add(i, 0F);
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

    public float getPoints(int i) {return pointsList.get(i);}

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

        Utils.nullCheck(judgePointsAllocationList.get(i).getName(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.pointsTypeTextView.setText(judgePointsAllocationList.get(i).getName());

            }

            @Override
            public void onNull() {
                viewHolder.pointsTypeTextView.setText("-");
            }
        });

        Utils.nullCheck(judgePointsAllocationList.get(i).getMaxPoints(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.maxPointsTextView.setText(String.valueOf(judgePointsAllocationList.get(i).getMaxPoints()));

                viewHolder.pointsSeekBar.setMax(judgePointsAllocationList.get(i).getMaxPoints()*2);

                viewHolder.pointsSeekBar.setThumb(new BitmapDrawable(context.getResources(), prepareThumbBitmap(0)));

                viewHolder.pointsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        seekBar.setThumb(new BitmapDrawable(context.getResources(), prepareThumbBitmap(progress / 2F)));
                        pointsList.add(i, seekBar.getProgress()/2F);
                        //Log.e("pointsadapter", String.valueOf(pointsList.get(i)));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
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
//        if (progress % 2 == 0) {
            canvas.drawText(String.valueOf(progress), (canvas.getWidth() - width) / 2, yPos, paint);
//        } else {
//            canvas.drawText(String.valueOf(progress/2), (canvas.getWidth() - width) / 2, yPos, paint);
//        }
        return bitmap;
    }


}

class PointsAllocationViewHolder {
    TextView pointsTypeTextView;
    TextView minPointsTextView;
    TextView maxPointsTextView;
    SeekBar pointsSeekBar;
}
