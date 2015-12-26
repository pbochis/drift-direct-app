package com.iancuio.driftdirect.utils;

import android.content.Context;

/**
 * Created by Soulstorm on 11/21/2015.
 */
public class Utils {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
