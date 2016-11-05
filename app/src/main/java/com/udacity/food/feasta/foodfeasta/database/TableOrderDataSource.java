package com.udacity.food.feasta.foodfeasta.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.model.TableOrder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public void deleteOrder(String name) {
        database.delete(TableOrderManager.TABLE_NAME, TableOrderManager.COLUMN_TABLE_NAME
                + " = '" + name+"'", null);
    }

    public void deleteCurrentOrder() {
        database.delete(TableOrderManager.TABLE_NAME, TableOrderManager.COLUMN_TABLE_NAME
                + " = '" + Constants.CURRENT_TABLE+"'", null);
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

    public List<String> getOccupiedTables() {
        List<String> tables = new ArrayList<String>();

        Cursor cursor = database.query(TableOrderManager.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String value = cursor.getString(1);
            tables.add(value);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return tables;
    }

    public List<String> getAvailableTables(){
        List<String> availableTables = new ArrayList<>();
        List<String> occupiedTables = getOccupiedTables();

        Iterator it = Constants.TABLE_MAP.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = (String) pair.getKey();
            if(!occupiedTables.contains(key)){
                availableTables.add(key);
            }
        }
        return availableTables;
    }

    private TableOrder cursorToComment(Cursor cursor) {
        TableOrder tableOrder = new TableOrder();
        tableOrder.setOrderId(cursor.getLong(0));
        tableOrder.setTableName(cursor.getString(1));
        tableOrder.setFoodItemName(cursor.getString(2));
        return tableOrder;
    }
}
