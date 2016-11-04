package com.udacity.food.feasta.foodfeasta.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jinal on 10/25/2016.
 */

public class TableOrderManager extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "TableOrders";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TABLE_NAME = "table_name";
    public static final String COLUMN_FOOD_ITEM_NAME = "item_name";

    private static final String DATABASE_NAME = "TableOrder.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_TABLE_NAME + " text not null, "
            + COLUMN_FOOD_ITEM_NAME + " text not null "
            + ");";

    public TableOrderManager(Context ctx) {
        super(ctx, TABLE_NAME, null, DATABASE_VERSION);
    }

    public TableOrderManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public TableOrderManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

        /*TableOrderDataSource dataSource = new TableOrderDataSource(mContext);
        dataSource.open();
        dataSource.createOrder("Table 1", "Chicken Momos");
        dataSource.createOrder("Table 1", "Samosa");
        dataSource.close();*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
