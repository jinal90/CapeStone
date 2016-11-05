package com.udacity.food.feasta.foodfeasta.helper.session;

import android.content.Context;

/**
 * Created by jinal on 11/4/2016.
 */

public interface SessionManagerInterface {

    String getSelectedTable(Context ctx);

    void setSelectedTable(Context ctx, String selectedTable);

    Integer getMenuDataVersion(Context ctx);

    void setMenuDataVersion(Context ctx, Integer menuDataVersion);
}
