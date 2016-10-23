
package com.udacity.food.feasta.foodfeasta.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FoodMenu implements Parcelable{

    private List<Fooditem> fooditem = new ArrayList<Fooditem>();

    protected FoodMenu(Parcel in) {
        fooditem = in.createTypedArrayList(Fooditem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(fooditem);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FoodMenu> CREATOR = new Creator<FoodMenu>() {
        @Override
        public FoodMenu createFromParcel(Parcel in) {
            return new FoodMenu(in);
        }

        @Override
        public FoodMenu[] newArray(int size) {
            return new FoodMenu[size];
        }
    };

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
