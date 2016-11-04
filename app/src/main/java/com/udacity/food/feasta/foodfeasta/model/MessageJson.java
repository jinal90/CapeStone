package com.udacity.food.feasta.foodfeasta.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jinal on 11/3/2016.
 */

public class MessageJson implements Parcelable{

    private String tableName;
    private Fooditem foodItem;

    public MessageJson(){

    }

    protected MessageJson(Parcel in) {
        tableName = in.readString();
        foodItem = in.readParcelable(Fooditem.class.getClassLoader());
    }

    public static final Creator<MessageJson> CREATOR = new Creator<MessageJson>() {
        @Override
        public MessageJson createFromParcel(Parcel in) {
            return new MessageJson(in);
        }

        @Override
        public MessageJson[] newArray(int size) {
            return new MessageJson[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tableName);
        dest.writeParcelable(foodItem, flags);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Fooditem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(Fooditem foodItem) {
        this.foodItem = foodItem;
    }
}
