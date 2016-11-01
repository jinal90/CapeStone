package com.udacity.food.feasta.foodfeasta.restaurant.manager.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.helper.Utility;
import com.udacity.food.feasta.foodfeasta.model.FoodMenu;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;
import com.udacity.food.feasta.foodfeasta.restaurant.manager.adapter.TableOrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TableOrderFragment extends Fragment {

    // TODO: Customize parameter argument names
    // TODO: Customize parameters
    private RecyclerView recyclerView;
    private FoodMenu menuObject;
    private static final String ARG_FOOD_TYPE = "food_type";
    private int mFoodType;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TableOrderFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TableOrderFragment newInstance(int foodType) {
        TableOrderFragment fragment = new TableOrderFragment();
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
            ProcessMenuData task = new ProcessMenuData();
            task.execute();
        }
        return view;
    }

    public void showContent() {
        // Set the adapter
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setAdapter(new TableOrderAdapter(menuObject));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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

    public class ProcessMenuData extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgressIndicator();
        }

        @Override
        protected Integer doInBackground(String... params) {

            if (Utility.isOnline(getActivity())) {
                try {
                    String response = Utility.getSavedStringDataFromPref(getActivity(), "MenuData");

                    String type = "";
                    if (!TextUtils.isEmpty(response)) {

                        if (mFoodType == Constants.FOOD_DESSERT) {
                            type = "desert";
                        } else if (mFoodType == Constants.FOOD_MAIN_COURSE) {
                            type = "main_course";
                        } else if (mFoodType == Constants.FOOD_STARTER) {
                            type = "starter";
                        }

                        Gson gson = new Gson();
                        FoodMenu fullMenu = gson.fromJson(response, FoodMenu.class);
                        menuObject = new FoodMenu();
                        List itemList = new ArrayList<Fooditem>();
                        if (fullMenu != null && fullMenu.getFooditem() != null
                                && fullMenu.getFooditem().size() > 0) {
                            for (int i = 0; i < fullMenu.getFooditem().size(); i++) {

                                if (type.equalsIgnoreCase(fullMenu.getFooditem().get(i).getCategory())) {
                                    itemList.add(fullMenu.getFooditem().get(i));
                                }
                            }
                            menuObject.setFooditem(itemList);
                            System.out.println("json -- " + menuObject.getFooditem().size());
                            return Constants.SUCCESS;
                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return Constants.FAILURE;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            switch (result) {
                case Constants.SUCCESS:
                    showContent();
                    break;
                case Constants.FAILURE:
                    //showErrorView();
                    break;
                default:
                    break;
            }
        }
    }
}
