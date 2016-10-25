package com.udacity.food.feasta.foodfeasta.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.udacity.food.feasta.foodfeasta.model.FoodMenu;

import java.util.HashMap;

/**
 * Created by jinal on 10/25/2016.
 */

public class OrderImpl implements OrderInterface{

    private FoodMenu currentOrder;

    private HashMap<String, FoodMenu> mCurrentTableOrder;
    private HashMap<String, Boolean> mCurrentTableStatus;

    public FoodMenu getCurrentOrder(Context ctx, String tableId) {

        if(currentOrder == null){
            String currentOrderJson = Utility.getSavedStringDataFromPref(ctx, tableId);
            Gson gson = new Gson();

            currentOrder = gson.fromJson(currentOrderJson, FoodMenu.class);
        }

        return currentOrder;
    }

    public void setCurrentOrder(FoodMenu currentOrder) {
        this.currentOrder = currentOrder;
    }


}
