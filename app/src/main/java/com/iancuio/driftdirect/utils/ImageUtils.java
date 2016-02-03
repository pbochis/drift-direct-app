package com.iancuio.driftdirect.utils;

import android.content.Context;
import android.widget.ImageView;
import com.iancuio.driftdirect.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Soulstorm on 1/23/2016.
 */
public class ImageUtils {
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
}

