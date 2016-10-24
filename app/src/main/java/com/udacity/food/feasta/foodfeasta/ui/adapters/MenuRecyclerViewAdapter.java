package com.udacity.food.feasta.foodfeasta.ui.adapters;

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
import com.udacity.food.feasta.foodfeasta.ui.fragment.MenuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder> {

    private final FoodMenu mFoodMenu;
    private final MenuFragment.OnListFragmentInteractionListener mListener;

    public MenuRecyclerViewAdapter(FoodMenu items, MenuFragment.OnListFragmentInteractionListener listener) {
        mFoodMenu = items;
        mListener = listener;
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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
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
