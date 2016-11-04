package com.udacity.food.feasta.foodfeasta.restaurant.manager.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.database.MenuDataManager;
import com.udacity.food.feasta.foodfeasta.database.TableOrderContentProvider;
import com.udacity.food.feasta.foodfeasta.database.TableOrderDataSource;
import com.udacity.food.feasta.foodfeasta.database.TableOrderManager;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;
import com.udacity.food.feasta.foodfeasta.restaurant.manager.adapter.TableOrderAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TableOrderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Customize parameter argument names
    // TODO: Customize parameters
    private RecyclerView recyclerView;
    private static final String ARG_TABLE_NAME = "Table_Name";
    private int mTableName;
    private TableOrderAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TableOrderFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TableOrderFragment newInstance(int tableName) {
        TableOrderFragment fragment = new TableOrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TABLE_NAME, tableName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTableName = getArguments().getInt(ARG_TABLE_NAME);
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
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter = new TableOrderAdapter();
        recyclerView.setAdapter(adapter);
        TableOrderDataSource dataSource = new TableOrderDataSource(getActivity());
        dataSource.open();
        dataSource.deleteAllItems();
        dataSource.createOrder("Table 1", "Chicken Momos");
        dataSource.createOrder("Table 1", "Samosa");
        dataSource.createOrder("Table 1", "Samosa");
        dataSource.createOrder("Table 1", "Apricot Ice Cream");
        dataSource.createOrder("Table 3", "Butter Chicken");
        dataSource.createOrder("Table 4", "Chicken Handi");
        dataSource.createOrder("Table 3", "Sandwich");
        dataSource.close();
        this.getLoaderManager().restartLoader(1, null, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String type = "";

        Iterator it = Constants.TABLE_MAP.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = (String) pair.getKey();
            Integer value = (Integer) pair.getValue();
            if (mTableName == value) {
                type = key;
            }
        }

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
                String[] selectionArgs = {type};
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Fooditem item);
    }
}
