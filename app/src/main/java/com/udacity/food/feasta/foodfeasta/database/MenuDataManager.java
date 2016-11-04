package com.udacity.food.feasta.foodfeasta.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jinal on 10/25/2016.
 */

public class MenuDataManager extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "FoodMenu";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "item_name";
    public static final String COLUMN_CATEGORY = "item_category";
    public static final String COLUMN_IMAGE = "item_image";
    public static final String COLUMN_SHORT_DESC = "item_short_desc";
    public static final String COLUMN_LONG_DESC = "item_long_desc";
    public static final String COLUMN_PRICE = "item_price";


    private static final String DATABASE_NAME = "FoodMenu.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_CATEGORY + " text not null,"
            + COLUMN_IMAGE + " text,"
            + COLUMN_SHORT_DESC + " text,"
            + COLUMN_LONG_DESC + " text,"
            + COLUMN_PRICE + " text"
            + ");";

    public MenuDataManager(Context ctx) {
        super(ctx, TABLE_NAME, null, DATABASE_VERSION);
    }

    public MenuDataManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MenuDataManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
