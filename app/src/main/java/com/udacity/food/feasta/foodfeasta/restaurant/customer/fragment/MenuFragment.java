package com.udacity.food.feasta.foodfeasta.restaurant.customer.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.database.MenuDataManager;
import com.udacity.food.feasta.foodfeasta.database.TableOrderContentProvider;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;
import com.udacity.food.feasta.foodfeasta.restaurant.customer.activity.LandingPageActivityCustomer;
import com.udacity.food.feasta.foodfeasta.restaurant.customer.adapters.MenuRecyclerViewAdapter;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MenuFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO: Customize parameter argument names
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private static final String ARG_FOOD_TYPE = "food_type";
    private int mFoodType;
    private MenuRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MenuFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MenuFragment newInstance(int foodType) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FOOD_TYPE, foodType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFoodType = getArguments().getInt(ARG_FOOD_TYPE);
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
        adapter = new MenuRecyclerViewAdapter(mListener,
                (LandingPageActivityCustomer) getActivity());
        recyclerView.setAdapter(adapter);
        this.getLoaderManager().restartLoader(1, null, this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String type = "";
        if (mFoodType == Constants.FOOD_DESSERT) {
            type = "desert";
        } else if (mFoodType == Constants.FOOD_MAIN_COURSE) {
            type = "main_course";
        } else if (mFoodType == Constants.FOOD_STARTER) {
            type = "starter";
        }

        String[] selectionArgs = {type};
        switch (id) {
            case 1:

                final Uri uri = Uri.parse(String.valueOf(TableOrderContentProvider.CONTENT_URI_3));
                return new CursorLoader(getActivity(), uri, null,
                        MenuDataManager.COLUMN_CATEGORY + " =?", selectionArgs, MenuDataManager.COLUMN_ID + " DESC");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case 1:
                adapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case 1:
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
        void onListFragmentInteraction(Fooditem item, ImageView img);
    }
}
