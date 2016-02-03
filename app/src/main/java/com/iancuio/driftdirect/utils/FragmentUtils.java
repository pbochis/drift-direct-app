package com.iancuio.driftdirect.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

/**
 * Created by Soulstorm on 1/23/2016.
 */
public class FragmentUtils {
    public static void setFragment(Fragment fragment, int fragmentName, Integer container, TextView navigationDrawerTitleTextView, FragmentActivity fragmentActivity) {
        navigationDrawerTitleTextView.setText(fragmentActivity.getString(fragmentName));
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .commit();
    }

    public static void setInitialFragment(Fragment fragment, int fragmentName, Integer container, TextView navigationDrawerTitleTextView, FragmentActivity fragmentActivity) {
        navigationDrawerTitleTextView.setText(fragmentActivity.getString(fragmentName));
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(container, fragment)
                .commit();
    }
}
