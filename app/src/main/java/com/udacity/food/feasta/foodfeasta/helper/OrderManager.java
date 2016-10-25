package com.udacity.food.feasta.foodfeasta.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.udacity.food.feasta.foodfeasta.FoodMenuApplication;
import com.udacity.food.feasta.foodfeasta.model.FoodMenu;

/**
 * Created by jinal on 10/25/2016.
 */

public class OrderManager {

    private static OrderInterface mOrderImpl;

    public static OrderInterface getInstance(){
        if(mOrderImpl == null){
            mOrderImpl = new OrderImpl();
        }

        return mOrderImpl;

    }

}