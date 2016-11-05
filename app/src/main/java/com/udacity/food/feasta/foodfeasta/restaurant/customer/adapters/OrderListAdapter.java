package com.udacity.food.feasta.foodfeasta.restaurant.customer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.model.FoodMenu;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jinal on 10/25/2016.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder> {

    private FoodMenu orderMenu;
    private Context mContext;

    public OrderListAdapter(Context ctx, FoodMenu itemList) {
        orderMenu = itemList;
        mContext = ctx;
    }


    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_cart_row, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {

        holder.mItem = orderMenu.getFooditem().get(position);
        holder.tvMenuItemName.setText(orderMenu.getFooditem().get(position).getName());
        holder.tvMenuItemPrice.setText(orderMenu.getFooditem().get(position).getPrice());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Item Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderMenu.getFooditem().size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvMenuItemName)
        TextView tvMenuItemName;
        @BindView(R.id.tvMenuItemPrice)
        TextView tvMenuItemPrice;

        public Fooditem mItem;
        public View mView;


        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }
}
