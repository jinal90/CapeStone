package com.udacity.food.feasta.foodfeasta.helper;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.udacity.food.feasta.foodfeasta.R;

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
        return prefs.getString(key, null);
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

    public static Dialog showAddItemDialog(Context context){
        LayoutInflater mInflater = LayoutInflater.from(context);
        View layout = mInflater.inflate(R.layout.order_layout, null);

        Dialog alertDialog = new Dialog(context);
        alertDialog.show();
        alertDialog.setContentView(layout);
        alertDialog.getWindow().setGravity(Gravity.CENTER);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

}
