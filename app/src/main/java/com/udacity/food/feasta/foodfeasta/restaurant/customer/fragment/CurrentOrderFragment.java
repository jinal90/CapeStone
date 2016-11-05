package com.udacity.food.feasta.foodfeasta.restaurant.customer.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.database.MenuDataManager;
import com.udacity.food.feasta.foodfeasta.database.TableOrderContentProvider;
import com.udacity.food.feasta.foodfeasta.database.TableOrderManager;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.restaurant.manager.adapter.TableOrderAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class CurrentOrderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    private static final String ARG_TABLE_NAME = "Table_Name";
    private String mTableName;
    private TableOrderAdapter adapter;

    public CurrentOrderFragment() {
    }

    public static CurrentOrderFragment newInstance(String tableName) {
        CurrentOrderFragment fragment = new CurrentOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TABLE_NAME, tableName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTableName = getArguments().getString(ARG_TABLE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_item_list, container, false);
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            showContent();
        }
        return view;
    }

    public void showContent() {
        // Set the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TableOrderAdapter();
        recyclerView.setAdapter(adapter);

        this.getLoaderManager().restartLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case 2:

                if (args != null) {
                    ArrayList<String> itemNamesList = args.getStringArrayList("ItemNames");
                    if (itemNamesList != null && itemNamesList.size() > 0) {
                        String names = "";
                        for (int i = 0; i < itemNamesList.size(); i++) {
                            names = names + "'" + itemNamesList.get(i);
                            if (i < itemNamesList.size() - 1)
                                names = names + "',";
                            else
                                names = names + "'";
                        }
                        final Uri uri1 = Uri.parse(String.valueOf(TableOrderContentProvider.CONTENT_URI_3));
                        return new CursorLoader(getActivity(), uri1, null,
                                MenuDataManager.COLUMN_NAME + " IN (" + names + ")", null, MenuDataManager.COLUMN_ID + " DESC");
                    }

                }

            case 1:
                String[] selectionArgs = {mTableName};
                final Uri uri2 = Uri.parse(String.valueOf(TableOrderContentProvider.CONTENT_URI_1));
                return new CursorLoader(getActivity(), uri2, null,
                        TableOrderManager.COLUMN_TABLE_NAME + " =?", selectionArgs, TableOrderManager.COLUMN_ID + " DESC");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 1:
                Bundle args = new Bundle();
                ArrayList<String> itemNames = new ArrayList<String>();
                data.moveToFirst();
                while (!data.isAfterLast()) {
                    itemNames.add(data.getString(2));
                    Log.d("ItemName - ", " item -- " + data.getString(2));
                    data.moveToNext();
                }
                // make sure to close the cursor
                data.close();
                args.putStringArrayList("ItemNames", itemNames);
                this.getLoaderManager().restartLoader(2, args, this);
                break;
            case 2:
                adapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case 2:
                adapter.swapCursor(null);
                break;
        }
    }

}
