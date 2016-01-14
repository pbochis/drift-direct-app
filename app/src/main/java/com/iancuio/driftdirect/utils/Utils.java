package com.iancuio.driftdirect.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.iancuio.driftdirect.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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

    public static void setListViewHeightBasedOnItems(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        //int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

            if (listItem != null) {
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(listView.getMeasuredWidth(), View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void loadNormalImage(Context context, String path, ImageView targetImage, Callback callback) {
        loadNormalImage(null, null, context, path, targetImage, callback);
    }

    public static void loadNormalImage(Integer height, Integer width, Context context, String path, ImageView targetImage, Callback callback) {
        if (height != null && width != null) {
            path = path + "?height=" + height + "&width=" + width;
        }
        Picasso.with(context).load(path).placeholder(R.drawable.placeholder).into(targetImage, callback);
    }

    public static void loadProfileImage(Context context, String path, ImageView targetImage, Callback callback) {
        loadProfileImage(null, null, context, path, targetImage, callback);
    }

    public static void loadProfileImage(Integer height, Integer width, Context context, String path, ImageView targetImage, Callback callback) {
        if (height != null && width != null) {
            path = path + "?height=" + height + "&width=" + width;
        }
        Picasso.with(context).load(path).placeholder(R.drawable.user_placeholder).into(targetImage, callback);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void nullCheck(Object object, NullCheck nullCheck) {
        if (object != null) {
            nullCheck.onNotNull();
        } else {
            nullCheck.onNull();
        }
    }

    public static void showAlertDialog(final Context context, String titleText, String messageText, final String toastText) {
        new AlertDialog.Builder(context)
                .setTitle(titleText)
                .setMessage(messageText)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Utils.nullCheck(toastText, new NullCheck() {
                            @Override
                            public void onNotNull() {
                                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNull() {

                            }
                        });
                        ((Activity)context).onBackPressed();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
