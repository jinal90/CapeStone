package com.udacity.food.feasta.foodfeasta.restaurant.manager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.model.FoodMenu;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TableOrderAdapter extends RecyclerView.Adapter<TableOrderAdapter.ViewHolder> {

    private final FoodMenu mFoodMenu;

    public TableOrderAdapter(FoodMenu items) {
        mFoodMenu = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mFoodMenu.getFooditem().get(position);
        holder.tvMenuItemName.setText(mFoodMenu.getFooditem().get(position).getName());
        holder.tvMenuItemDescription.setText(mFoodMenu.getFooditem().get(position).getShort_desc());
        holder.tvMenuItemPrice.setText(mFoodMenu.getFooditem().get(position).getPrice());

        Picasso.with(holder.itemView.getContext())
                .load(mFoodMenu.getFooditem().get(position).getImage())
                .into(holder.imgFoodItem);

    }

    @Override
    public int getItemCount() {
        return mFoodMenu.getFooditem().size();
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

        @Override
        public String toString() {
            return super.toString() + " 'Something Something'";
        }
    }
}
