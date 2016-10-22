
package com.udacity.food.feasta.foodfeasta.model;

import java.util.ArrayList;
import java.util.List;

public class FoodMenu {

    private List<Fooditem> fooditem = new ArrayList<Fooditem>();

    /**
     * 
     * @return
     *     The fooditem
     */
    public List<Fooditem> getFooditem() {
        return fooditem;
    }

    /**
     * 
     * @param fooditem
     *     The fooditem
     */
    public void setFooditem(List<Fooditem> fooditem) {
        this.fooditem = fooditem;
    }

}
