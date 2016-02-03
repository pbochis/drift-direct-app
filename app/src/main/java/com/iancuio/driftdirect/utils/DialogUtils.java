package com.iancuio.driftdirect.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.iancuio.driftdirect.utils.interfaces.NullCheck;

/**
 * Created by Soulstorm on 1/23/2016.
 */
public class DialogUtils {
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
                        ((Activity) context).onBackPressed();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
