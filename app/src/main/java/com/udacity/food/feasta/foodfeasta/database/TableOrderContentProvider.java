package com.udacity.food.feasta.foodfeasta.database;

import android.content.ContentProvider;
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
    public static final Uri CONTENT_URI_1 = Uri.parse("content://" + PROVIDER_NAME + "/orders");
    public static final Uri CONTENT_URI_2 = Uri.parse("content://" + PROVIDER_NAME + "/tables");
    public static final Uri CONTENT_URI_3 = Uri.parse("content://" + PROVIDER_NAME + "/fooditems");

    static final int ORDERS = 1;
    static final int ORDER = 2;
    static final int RESTAURANT_TABLES = 3;
    static final int RESTAURANT_TABLE = 4;
    static final int MENU_ITEMS = 5;
    static final int MENU_ITEM = 6;

    SQLiteDatabase db;
    TableOrderManager dbTableOrderHelper;
    MenuDataManager dbMenuItemHelper;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "orders", ORDERS);
        uriMatcher.addURI(PROVIDER_NAME, "orders/#", ORDER);

        uriMatcher.addURI(PROVIDER_NAME, "tables", RESTAURANT_TABLES);
        uriMatcher.addURI(PROVIDER_NAME, "tables/#", RESTAURANT_TABLE);

        uriMatcher.addURI(PROVIDER_NAME, "fooditems", MENU_ITEMS);
        uriMatcher.addURI(PROVIDER_NAME, "fooditems/#", MENU_ITEM);

    }

    @Override
    public boolean onCreate() {
        dbTableOrderHelper = new TableOrderManager(getContext());
        dbMenuItemHelper = new MenuDataManager(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbMenuItemHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        String id;

        switch (uriMatcher.match(uri)) {

            case MENU_ITEMS:
            case MENU_ITEM:
                queryBuilder.setTables(MenuDataManager.TABLE_NAME);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

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

        SQLiteDatabase db = dbMenuItemHelper.getWritableDatabase();
        long id = 0;
        switch (uriMatcher.match(uri)) {
            case MENU_ITEM:
            case MENU_ITEMS:
                id = db.insert(MenuDataManager.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        return Uri.parse(uri.toString() + "/" + id);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbMenuItemHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case MENU_ITEMS:
                //do nothing
                break;
            case MENU_ITEM:
                String id = uri.getPathSegments().get(1);
                selection = MenuDataManager.COLUMN_ID + " = " + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete(MenuDataManager.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbMenuItemHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case MENU_ITEMS:
                //do nothing
                break;
            case MENU_ITEM:
                String id = uri.getPathSegments().get(1);
                selection = MenuDataManager.COLUMN_ID + " = " + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update(MenuDataManager.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }
}
