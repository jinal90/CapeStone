package com.udacity.food.feasta.foodfeasta.model;

/**
 * Created by jinal on 10/25/2016.
 */

public class TableOrder {

    private long orderId;
    private String tableName;
    private String foodItemName;


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }
}
