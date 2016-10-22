package com.udacity.food.feasta.foodfeasta.helper;

import android.content.Context;
import android.content.SharedPreferences;

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

}
