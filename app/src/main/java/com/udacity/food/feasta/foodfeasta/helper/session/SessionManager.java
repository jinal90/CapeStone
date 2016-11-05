package com.udacity.food.feasta.foodfeasta.helper.session;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.helper.Utility;

/**
 * Created by jinal on 11/4/2016.
 */

public class SessionManager implements SessionManagerInterface{

    private String selectedTable;
    private Integer menuDataVersion;

    public String getSelectedTable(Context ctx) {

        if(TextUtils.isEmpty(selectedTable)){

            selectedTable = Utility.getSavedStringDataFromPref(ctx, ctx.getString(R.string.key_selectedTable));
        }
        return selectedTable;
    }

    public void setSelectedTable(Context ctx, String selectedTable) {
        this.selectedTable = selectedTable;
        Utility.saveStringDataInPref(ctx, ctx.getString(R.string.key_selectedTable), selectedTable);
    }

    public Integer getMenuDataVersion(Context ctx) {

        if(menuDataVersion == null){
            menuDataVersion = Utility.getSavedIntDataFromPref(ctx, ctx.getString(R.string.key_menuVersion));
        }
        return menuDataVersion;
    }

    public void setMenuDataVersion(Context ctx, Integer menuDataVersion) {
        this.menuDataVersion = menuDataVersion;
        Utility.saveIntDataInPref(ctx, ctx.getString(R.string.key_menuVersion), menuDataVersion);
    }
}
