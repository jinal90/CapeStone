package com.udacity.food.feasta.foodfeasta.restaurant.manager.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;
import com.udacity.food.feasta.foodfeasta.table.customer.ui.adapters.RecyclerViewCursorAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TableOrderAdapter extends RecyclerViewCursorAdapter<TableOrderAdapter.ViewHolder> {


    public TableOrderAdapter() {
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
        }


        @Override
        public String toString() {
            return super.toString() + " 'Something Something'";
        }
    }
}
