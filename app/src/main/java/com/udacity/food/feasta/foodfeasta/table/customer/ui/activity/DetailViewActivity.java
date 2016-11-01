package com.udacity.food.feasta.foodfeasta.table.customer.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;
import com.udacity.food.feasta.foodfeasta.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailViewActivity extends BaseActivity {

    @BindView(R.id.fab)
    FloatingActionButton fabAdd;
    @BindView(R.id.photo)
    ImageView imgItemPhoto;
    @BindView(R.id.tvItemDescription)
    TextView tvItemDescription;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Fooditem mFoodItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setupUI();
        setupListeners();
    }

    @Override
    public void setupUI() {
        imgItemPhoto.setImageResource(R.drawable.item_afghani_chicken);

        if (getIntent() != null && getIntent().hasExtra(Constants.SELECTED_FOOD_ITEM)) {
            mFoodItem = (Fooditem) getIntent().getParcelableExtra(Constants.SELECTED_FOOD_ITEM);

        }

        if (mFoodItem != null) {

            if (!TextUtils.isEmpty(mFoodItem.getImage())) {
                Picasso.with(this)
                        .load(mFoodItem.getImage())
                        .into(imgItemPhoto);
            }

            if (!TextUtils.isEmpty(mFoodItem.getName())) {
                toolbar.setTitle(mFoodItem.getName());
            }
        }


    }

    @Override
    public void setupListeners() {

    }
}
