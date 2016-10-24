package com.udacity.food.feasta.foodfeasta.helper;

import android.content.Context;

import com.udacity.food.feasta.foodfeasta.model.FoodMenu;

/**
 * Created by jinal on 10/25/2016.
 */

public interface OrderInterface {

    FoodMenu getCurrentOrder(Context ctx);

    void setCurrentOrder(FoodMenu currentOrder);

}
