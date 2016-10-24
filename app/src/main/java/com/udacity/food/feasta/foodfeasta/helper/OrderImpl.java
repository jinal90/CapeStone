package com.udacity.food.feasta.foodfeasta.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.udacity.food.feasta.foodfeasta.model.FoodMenu;

/**
 * Created by jinal on 10/25/2016.
 */

public class OrderImpl{

    private FoodMenu currentOrder;

    public FoodMenu getCurrentOrder(Context ctx) {

        if(currentOrder == null){
            String currentOrderJson = Utility.getSavedStringDataFromPref(ctx, "selectedTable");
            Gson gson = new Gson();

            currentOrder = gson.fromJson(currentOrderJson, FoodMenu.class);
        }

        return currentOrder;
    }

    public void setCurrentOrder(FoodMenu currentOrder) {
        this.currentOrder = currentOrder;
    }
}