package com.udacity.food.feasta.foodfeasta.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.udacity.food.feasta.foodfeasta.R;

import java.util.concurrent.Callable;

/**
 * Created by jinal on 10/22/2016.
 */

public class Utility {

    public static void saveStringDataInPref(Context context, String key,
                                            String data) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        prefs.edit().putString(key, data).commit();
    }

    public static String getSavedStringDataFromPref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void saveIntDataInPref(Context context, String key,
                                            int data) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        prefs.edit().putInt(key, data).commit();
    }

    public static int getSavedIntDataFromPref(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(
                context.getPackageName(), Context.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info != null)
            return info.isConnected();
        else
            return false;
    }

    public static AlertDialog showTwoButtonDialog(Context context, String title, String message,
                                                  final String positiveBtn, final Callable positiveBtnCallable,
                                                  final String negativeBtn, final Callable negativeBtnCallable) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (positiveBtnCallable != null)
                    try {
                        positiveBtnCallable.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
        builder.setNegativeButton(negativeBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (negativeBtnCallable != null)
                    try {
                        negativeBtnCallable.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                else
                    dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }

    public static void showSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();

        // Changing snackbar background color
        sbView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorAccent));

        // Changing snackbar text color
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.primary_text));
        snackbar.show();
    }

}
