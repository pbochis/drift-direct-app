package com.iancuio.driftdirect.sharedAdapters.viewPagerAdapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.championship.judge.ChampionshipJudgeParticipation;
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.RestUrls;
import com.squareup.picasso.Callback;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScreenSlidePagerAdapterCustomView extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private List<ChampionshipJudgeParticipation> championshipJudgeParticipationList;

    public ScreenSlidePagerAdapterCustomView(FragmentManager fm, List<Fragment> fragments, List<ChampionshipJudgeParticipation> championshipJudgeParticipations) {
        super(fm);
        this.fragments = fragments;
        this.championshipJudgeParticipationList = championshipJudgeParticipations;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    public View getTabView(int position, Context context) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.judges_custom_tab_layout, null);
        CircleImageView judgePictureImageView = (CircleImageView) v.findViewById(R.id.imageView_judgesCustomTabLayout_judgePicture);
        final ProgressBar imageProgressBar = (ProgressBar) v.findViewById(R.id.progressBar_judgesCustomTabLayout_progressBar);

        if (position == 0) {
            judgePictureImageView.setBorderColor(context.getResources().getColor(R.color.colorChampionships));
        }

        ImageUtils.loadNormalImage(300, 300, context, RestUrls.FILE + championshipJudgeParticipationList.get(position).getJudge().getProfilePicture(), judgePictureImageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("succes", "image succes");
                imageProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.e("error", "imageError");
                imageProgressBar.setVisibility(View.GONE);
            }
        });
        return v;
    }
}