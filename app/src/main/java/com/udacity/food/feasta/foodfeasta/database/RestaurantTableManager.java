package com.udacity.food.feasta.foodfeasta.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.food.feasta.foodfeasta.helper.Constants;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by jinal on 10/25/2016.
 */

public class RestaurantTableManager extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "RestaurantTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "table_name";


    private static final String DATABASE_NAME = "RestaurantTable.db";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null "
            + ");";

    public RestaurantTableManager(Context ctx) {
        super(ctx, TABLE_NAME, null, DATABASE_VERSION);
        mContext = ctx;
    }

    public RestaurantTableManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public RestaurantTableManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        insertTableEntries();
    }

    public void insertTableEntries() {
        SQLiteDatabase database;
        database = getWritableDatabase();

        Iterator it = Constants.TABLE_MAP.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = (String) pair.getKey();
            Integer value = (Integer) pair.getValue();

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, key);
            long insertId = database.insert(TABLE_NAME, null,
                    contentValues);
            String[] allColumns = {COLUMN_NAME};
            Cursor cursor = database.query(TABLE_NAME,
                    allColumns, COLUMN_ID + " = " + insertId, null,
                    null, null, null);
        }
        close();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
