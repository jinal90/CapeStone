package com.udacity.food.feasta.foodfeasta.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.udacity.food.feasta.foodfeasta.model.TableOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinal on 10/25/2016.
 */

public class TableOrderDataSource {

    // Database fields
    private SQLiteDatabase database;
    private TableOrderManager dbHelper;
    private String[] allColumns = { TableOrderManager.COLUMN_ID,
            TableOrderManager.COLUMN_TABLE_NAME, TableOrderManager.COLUMN_FOOD_ITEM_NAME };

    public TableOrderDataSource(Context context) {
        dbHelper = new TableOrderManager(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public TableOrder createOrder(String tableName, String foodName) {
        ContentValues values = new ContentValues();
        values.put(TableOrderManager.COLUMN_TABLE_NAME, tableName);
        values.put(TableOrderManager.COLUMN_FOOD_ITEM_NAME, foodName);
        long insertId = database.insert(TableOrderManager.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(TableOrderManager.TABLE_NAME,
                allColumns, TableOrderManager.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        TableOrder newOrder = cursorToComment(cursor);
        cursor.close();
        return newOrder;
    }

    public void deleteOrder(TableOrder order) {
        long tableId = order.getOrderId();
        System.out.println("TableOrder deleted with id: " + tableId);
        database.delete(TableOrderManager.TABLE_NAME, TableOrderManager.COLUMN_ID
                + " = " + tableId, null);
    }

    public void deleteAllItems(){
        database.execSQL("delete from "+ TableOrderManager.TABLE_NAME);

    }

    public List<TableOrder> getAllOrders() {
        List<TableOrder> tableOrders = new ArrayList<TableOrder>();

        Cursor cursor = database.query(TableOrderManager.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TableOrder comment = cursorToComment(cursor);
            tableOrders.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tableOrders;
    }

    private TableOrder cursorToComment(Cursor cursor) {
        TableOrder tableOrder = new TableOrder();
        tableOrder.setOrderId(cursor.getLong(0));
        tableOrder.setTableName(cursor.getString(1));
        tableOrder.setFoodItemName(cursor.getString(2));
        return tableOrder;
    }
}
