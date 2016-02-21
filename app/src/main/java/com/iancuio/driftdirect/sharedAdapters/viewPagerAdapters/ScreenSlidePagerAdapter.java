package com.iancuio.driftdirect.sharedAdapters.viewPagerAdapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.utils.ImageUtils;
import com.iancuio.driftdirect.utils.RestUrls;
import com.squareup.picasso.Callback;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    private String[] tabTitles;

    public ScreenSlidePagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] tabTitles) {
        super(fm);
        this.fragments = fragments;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public View getTabView(int position, Context context) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tabName = (TextView) v.findViewById(R.id.custom_text);

        tabName.setText(String.valueOf(tabTitles[position]));
        return v;
    }
}