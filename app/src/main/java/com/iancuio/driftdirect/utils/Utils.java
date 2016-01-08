package com.iancuio.driftdirect.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

//    public static boolean setListViewHeightBasedOnItems(ListView listView) {
//
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter != null) {
//
//            int numberOfItems = listAdapter.getCount();
//
//            // Get total height of all items.
//            int totalItemsHeight = 0;
//            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
//                View item = listAdapter.getView(itemPos, null, listView);
//                item.measure(0, 0);
//                totalItemsHeight += item.getMeasuredHeight();
//            }
//
//            // Get total height of all item dividers.
//            int totalDividersHeight = listView.getDividerHeight() *
//                    (numberOfItems - 1);
//            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();
//
//            // Set list height.
//            ViewGroup.LayoutParams params = listView.getLayoutParams();
//            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
//            listView.setLayoutParams(params);
//            listView.requestLayout();
//
//            return true;
//
//        } else {
//            return false;
//        }
//
//    }

//    public static void setListViewHeightBasedOnItems(ListView listView) {
//        ListAdapter listAdapter = listView.getAdapter();
//        if (listAdapter == null) {
//            // pre-condition
//            return;
//        }
//
//        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY);
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//
//            if(listItem != null){
//                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
//                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
//                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//                totalHeight += listItem.getMeasuredHeight();
//            }
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + totalPadding;
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//    }

//    public static void setListViewHeightBasedOnItems(ListView listView) {
//
//        ListAdapter mAdapter = listView.getAdapter();
//
//        int totalHeight = 0;
//
//        for (int i = 0; i < mAdapter.getCount(); i++) {
//            View mView = mAdapter.getView(i, null, listView);
//
//            mView.measure(
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//
//            totalHeight += mView.getHeight();
//
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();
//        params.height = totalHeight
//                + (listView.getDividerHeight() * (mAdapter.getCount() - 1)) + totalPadding;
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//
//    }

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

    public static void loadImage(Context context, String path, ImageView targetImage, Callback callback) {
        loadImage(null, null, context, path, targetImage, callback);
    }

    public static void loadImage(Integer height, Integer width, Context context, String path, ImageView targetImage, Callback callback) {
        if (height != null && width != null) {
            path = path + "?height=" + height + "&width=" + width;
        }
        Picasso.with(context).load(path).noPlaceholder().into(targetImage, callback);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
