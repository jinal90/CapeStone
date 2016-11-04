package com.udacity.food.feasta.foodfeasta.table.customer.ui.adapters;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.helper.SessionFactory;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;
import com.udacity.food.feasta.foodfeasta.model.TableOrder;
import com.udacity.food.feasta.foodfeasta.table.customer.ui.activity.LandingPageActivityCustomer;
import com.udacity.food.feasta.foodfeasta.table.customer.ui.fragment.MenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MenuRecyclerViewAdapter extends RecyclerViewCursorAdapter<MenuRecyclerViewAdapter.ViewHolder> {

    private final MenuFragment.OnListFragmentInteractionListener mListener;
    private LandingPageActivityCustomer publishActivity;

    public MenuRecyclerViewAdapter(MenuFragment.OnListFragmentInteractionListener listener,
                                   LandingPageActivityCustomer activity) {
        mListener = listener;
        publishActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        Fooditem foodItem = new Fooditem();
        foodItem.setName(cursor.getString(1));
        foodItem.setCategory(cursor.getString(2));
        foodItem.setImage(cursor.getString(3));
        foodItem.setShort_desc(cursor.getString(4));
        foodItem.setLong_desc(cursor.getString(5));
        foodItem.setPrice(cursor.getString(6));

        holder.bindDataToView(foodItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.tvMenuItemName)
        TextView tvMenuItemName;
        @BindView(R.id.tvMenuItemDescription)
        TextView tvMenuItemDescription;
        @BindView(R.id.tvMenuItemPrice)
        TextView tvMenuItemPrice;
        @BindView(R.id.imgFoodItem)
        ImageView imgFoodItem;
        @BindView(R.id.imgAddRemoveItem)
        ImageView imgAddRemoveItem;

        public Fooditem mItem;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
            mView = view;
        }

        public void bindDataToView(final Fooditem foodItem) {
            mItem = foodItem;
            tvMenuItemName.setText(foodItem.getName());
            tvMenuItemDescription.setText(foodItem.getShort_desc());
            tvMenuItemPrice.setText(foodItem.getPrice());

            Picasso.with(itemView.getContext())
                    .load(foodItem.getImage())
                    .into(imgFoodItem);

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(foodItem);
                    }
                }
            });

            imgAddRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(publishActivity);

                    builder.setTitle("Order Confirmation");

                    builder.setMessage("Do you want to order " + foodItem.getName() +
                            "?? \n It costs " + foodItem.getPrice() + ".");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(itemView.getContext(), "Added", Toast.LENGTH_SHORT).show();
                            TableOrder message = new TableOrder();
                            //message.setTableName("Table 1");
                            message.setTableName(SessionFactory.getInstance().getSelectedTable());
                            message.setFoodItemName(foodItem.getName());
                            Gson gson = new Gson();
                            String msgJson = gson.toJson(message);
                            publishActivity.publish(msgJson);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();

                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " 'Something Something'";
        }
    }
}
