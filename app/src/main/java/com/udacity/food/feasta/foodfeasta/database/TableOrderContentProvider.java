package com.udacity.food.feasta.foodfeasta.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by jinal on 10/26/2016.
 */

public class TableOrderContentProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.udacity.food.feasta.foodfeasta.database";
    static final String URL = "content://" + PROVIDER_NAME + "/orders";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final int ORDERS = 1;
    static final int ORDER = 2;
    static final int RESTAURANT_TABLES = 3;
    static final int RESTAURANT_TABLE = 4;
    static final int MENU_ITEMS = 5;
    static final int MENU_ITEM = 6;

    SQLiteDatabase db;
    TableOrderManager dbHelper;

    private static UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, TableOrderManager.TABLE_NAME, ORDER);

    }

    @Override
    public boolean onCreate() {
        dbHelper = new TableOrderManager(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor;

        /*if(uriMatcher.match(uri) == ORDER){
            cursor = db.query(TableOrderManager.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }*/

        db = dbHelper.getReadableDatabase();
        cursor = db.query(TableOrderManager.TABLE_NAME, projection, selection, selectionArgs, null,
                null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        db = dbHelper.getWritableDatabase();

        if(uriMatcher.match(uri) == ORDER){
            db.insert(TableOrderManager.TABLE_NAME, null, values);
        }

        db.close();

        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
