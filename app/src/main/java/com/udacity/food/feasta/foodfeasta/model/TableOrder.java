package com.udacity.food.feasta.foodfeasta.model;

/**
 * Created by jinal on 10/25/2016.
 */

public class TableOrder {

    private long orderId;
    private int tableId;
    private int foodItemId;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        this.foodItemId = foodItemId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
