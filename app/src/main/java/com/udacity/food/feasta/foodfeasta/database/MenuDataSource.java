package com.udacity.food.feasta.foodfeasta.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.udacity.food.feasta.foodfeasta.model.Fooditem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinal on 11/03/2016.
 */

public class MenuDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MenuDataManager dbHelper;
    private String[] allColumns = {MenuDataManager.COLUMN_ID,
            MenuDataManager.COLUMN_NAME, MenuDataManager.COLUMN_CATEGORY,
            MenuDataManager.COLUMN_IMAGE, MenuDataManager.COLUMN_SHORT_DESC,
            MenuDataManager.COLUMN_LONG_DESC, MenuDataManager.COLUMN_PRICE};

    public MenuDataSource(Context context) {
        dbHelper = new MenuDataManager(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Fooditem createItem(Fooditem fooditem) {
        ContentValues values = new ContentValues();
        values.put(MenuDataManager.COLUMN_NAME, fooditem.getName());
        values.put(MenuDataManager.COLUMN_CATEGORY, fooditem.getCategory());
        values.put(MenuDataManager.COLUMN_IMAGE, fooditem.getImage());
        values.put(MenuDataManager.COLUMN_SHORT_DESC, fooditem.getShort_desc());
        values.put(MenuDataManager.COLUMN_LONG_DESC, fooditem.getLong_desc());
        values.put(MenuDataManager.COLUMN_PRICE, fooditem.getPrice());
        long insertId = database.insert(MenuDataManager.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(MenuDataManager.TABLE_NAME,
                allColumns, MenuDataManager.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        /*String[] selectionArgs = {fooditem.getName()};
        Cursor cursor = database.query(MenuDataManager.TABLE_NAME,
                allColumns, MenuDataManager.COLUMN_NAME +"=?", selectionArgs,
                null, null, null);*/
        cursor.moveToFirst();
        Fooditem item = cursorToFoodItem(cursor);
        cursor.close();
        return item;
    }

    public void deleteItem(Fooditem fooditem) {
        String item_name = fooditem.getName();
        String item_category = fooditem.getCategory();
        database.delete(MenuDataManager.TABLE_NAME, MenuDataManager.COLUMN_NAME
                + " = " + item_name + " AND " + MenuDataManager.COLUMN_CATEGORY
                + " = " + item_category, null);
    }

    public void deleteAllItems(){
        database.execSQL("delete from "+ MenuDataManager.TABLE_NAME);

    }

    public List<Fooditem> getAllFoodItems() {
        List<Fooditem> fooditems = new ArrayList<Fooditem>();

        Cursor cursor = database.query(MenuDataManager.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Fooditem item = cursorToFoodItem(cursor);
            fooditems.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return fooditems;
    }

    private Fooditem cursorToFoodItem(Cursor cursor) {
        Fooditem foodItem = new Fooditem();
        foodItem.setName(cursor.getString(1));
        foodItem.setCategory(cursor.getString(2));
        foodItem.setImage(cursor.getString(3));
        foodItem.setShort_desc(cursor.getString(4));
        foodItem.setLong_desc(cursor.getString(5));
        foodItem.setPrice(cursor.getString(6));
        return foodItem;
    }
}
