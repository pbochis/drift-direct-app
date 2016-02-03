package com.iancuio.driftdirect.round.navigationView.top32_16.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.round.playoffs.BattleGraphicDisplay;
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
public class Top16Top32Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<BattleGraphicDisplay> battleGraphicDisplayList;


    public Top16Top32Adapter (Context context, List<BattleGraphicDisplay> battleGraphicDisplayList) {
        this.context = context;
        this.battleGraphicDisplayList = battleGraphicDisplayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return battleGraphicDisplayList.size();
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
        final Top16Top32ViewHolder viewHolder;

        if (listItem == null) {
            viewHolder = new Top16Top32ViewHolder();

            listItem = inflater.inflate(R.layout.listview_row_top16top32, viewGroup, false);
            viewHolder.firstDriverFlag = (ImageView) listItem.findViewById(R.id.imageView_top16Top32Layout_firstDriverFlag);
            viewHolder.firstDriverName = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_firstDriverName);
            viewHolder.firstDriverPicture = (CircleImageView) listItem.findViewById(R.id.imageView_top16Yop32Layout_firstBadgePicture);
            viewHolder.firstDriverPictureProgressBar = (ProgressBar) listItem.findViewById(R.id.progressBar_top16Yop32Layout_firstBadgePictureProgressBar);
            viewHolder.firstDriverOrder = (TextView) listItem.findViewById(R.id.textView_top16Yop32Layout_firstBadgeOrder);
            viewHolder.firstDriverStatus = (TextView) listItem.findViewById(R.id.textView_top16Yop32Layout_firstBadgeText);
            viewHolder.firstDriverCarModel = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_firstDriverCarModel);
            viewHolder.firstDriverCarHP = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_firstDriverCarHP);
            viewHolder.firstDriverWinner = (TextView) listItem.findViewById(R.id.textView_top16Yop32Layout_firstBadgeText);

            viewHolder.secondDriverFlag = (ImageView) listItem.findViewById(R.id.imageView_top16Top32Layout_secondDriverFlag);
            viewHolder.secondDriverName = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_secondDriverName);
            viewHolder.secondDriverPicture = (CircleImageView) listItem.findViewById(R.id.imageView_top16Yop32Layout_secondBadgePicture);
            viewHolder.secondDriverPictureProgressBar = (ProgressBar) listItem.findViewById(R.id.progressBar_top16Yop32Layout_secondBadgePictureProgressBar);
            viewHolder.secondDriverOrder = (TextView) listItem.findViewById(R.id.textView_top16Yop32Layout_secondBadgeOrder);
            viewHolder.secondDriverStatus = (TextView) listItem.findViewById(R.id.textView_top16Yop32Layout_secondBadgeText);
            viewHolder.secondDriverCarModel = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_secondDriverCarModel);
            viewHolder.secondDriverCarHP = (TextView) listItem.findViewById(R.id.textView_top16Top32Layout_secondDriverCarHP);
            viewHolder.secondDriverWinner = (TextView) listItem.findViewById(R.id.textView_top16Yop32Layout_secondBadgeText);

            listItem.setTag(viewHolder);
        } else {
            viewHolder = (Top16Top32ViewHolder) view.getTag();
        }


        ImageUtils.loadNormalImage(100, 100, context, RestUrls.FILE + battleGraphicDisplayList.get(i).getDriver1().getDriver().getCountry(), viewHolder.firstDriverFlag, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

        viewHolder.firstDriverName.setText(battleGraphicDisplayList.get(i).getDriver1().getDriver().getFirstName() + " " + battleGraphicDisplayList.get(i).getDriver1().getDriver().getLastName());

        ImageUtils.loadNormalImage(200, 200, context, RestUrls.FILE + battleGraphicDisplayList.get(i).getDriver1().getDriver().getProfilePicture(), viewHolder.firstDriverPicture, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.firstDriverPictureProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                viewHolder.firstDriverPictureProgressBar.setVisibility(View.GONE);

            }
        });

        Utils.nullCheck(battleGraphicDisplayList.get(i).getDriver1().getRanking(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.firstDriverOrder.setText(String.valueOf(battleGraphicDisplayList.get(i).getDriver1().getRanking()));

            }

            @Override
            public void onNull() {
                viewHolder.firstDriverOrder.setText("-");
            }
        });

        Utils.nullCheck(battleGraphicDisplayList.get(i).getDriver1().getDriver().getDriverDetails().getMake(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.firstDriverCarModel.setText(battleGraphicDisplayList.get(i).getDriver1().getDriver().getDriverDetails().getMake() + " " + battleGraphicDisplayList.get(i).getDriver1().getDriver().getDriverDetails().getModel());

            }

            @Override
            public void onNull() {
                viewHolder.firstDriverCarModel.setText("-");
            }
        });
        //viewHolder.firstDriverStatus

        Utils.nullCheck(battleGraphicDisplayList.get(i).getDriver1().getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.firstDriverCarHP.setText(String.valueOf(battleGraphicDisplayList.get(i).getDriver1().getDriver().getDriverDetails().getHorsePower() + "HP"));

            }

            @Override
            public void onNull() {
                viewHolder.firstDriverCarHP.setText("-");
            }
        });

        ImageUtils.loadNormalImage(100, 100, context, RestUrls.FILE + battleGraphicDisplayList.get(i).getDriver2().getDriver().getCountry(), viewHolder.secondDriverFlag, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });


        viewHolder.secondDriverName.setText(battleGraphicDisplayList.get(i).getDriver2().getDriver().getFirstName() + " " + battleGraphicDisplayList.get(i).getDriver2().getDriver().getLastName());

        ImageUtils.loadNormalImage(200, 200, context, RestUrls.FILE + battleGraphicDisplayList.get(i).getDriver2().getDriver().getProfilePicture(), viewHolder.secondDriverPicture, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.secondDriverPictureProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                viewHolder.secondDriverPictureProgressBar.setVisibility(View.GONE);

            }
        });

        Utils.nullCheck(battleGraphicDisplayList.get(i).getDriver2().getRanking(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.secondDriverOrder.setText(String.valueOf(battleGraphicDisplayList.get(i).getDriver2().getRanking()));
            }

            @Override
            public void onNull() {
                viewHolder.secondDriverOrder.setText("-");
            }
        });
        //viewHolder.secondDriverStatus

        Utils.nullCheck(battleGraphicDisplayList.get(i).getDriver2().getDriver().getDriverDetails().getMake(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.secondDriverCarModel.setText(battleGraphicDisplayList.get(i).getDriver2().getDriver().getDriverDetails().getMake() + " " + battleGraphicDisplayList.get(i).getDriver2().getDriver().getDriverDetails().getModel());

            }

            @Override
            public void onNull() {
                viewHolder.secondDriverCarModel.setText("-");
            }
        });

        Utils.nullCheck(battleGraphicDisplayList.get(i).getDriver2().getDriver().getDriverDetails().getHorsePower(), new NullCheck() {
            @Override
            public void onNotNull() {
                viewHolder.secondDriverCarHP.setText(String.valueOf(battleGraphicDisplayList.get(i).getDriver2().getDriver().getDriverDetails().getHorsePower() + "HP"));

            }

            @Override
            public void onNull() {
                viewHolder.secondDriverCarHP.setText("-");
            }
        });

        Utils.nullCheck(battleGraphicDisplayList.get(i).getWinner(), new NullCheck() {
            @Override
            public void onNotNull() {
                if (battleGraphicDisplayList.get(i).getDriver1().getId() == battleGraphicDisplayList.get(i).getWinner().getId()) {
                    viewHolder.firstDriverWinner.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.secondDriverWinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNull() {

            }
        });

        return listItem;
    }
}



class Top16Top32ViewHolder {
    ImageView firstDriverFlag;
    TextView firstDriverName;
    CircleImageView firstDriverPicture;
    ProgressBar firstDriverPictureProgressBar;
    TextView firstDriverOrder;
    TextView firstDriverStatus;
    TextView firstDriverCarModel;
    TextView firstDriverCarHP;
    TextView firstDriverWinner;

    ImageView secondDriverFlag;
    TextView secondDriverName;
    CircleImageView secondDriverPicture;
    ProgressBar secondDriverPictureProgressBar;
    TextView secondDriverOrder;
    TextView secondDriverStatus;
    TextView secondDriverCarModel;
    TextView secondDriverCarHP;
    TextView secondDriverWinner;

}
