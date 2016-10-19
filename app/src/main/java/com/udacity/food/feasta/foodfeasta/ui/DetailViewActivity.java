package com.udacity.food.feasta.foodfeasta.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.udacity.food.feasta.foodfeasta.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailViewActivity extends BaseActivity {

    @BindView(R.id.fab)
    FloatingActionButton fabAdd;
    @BindView(R.id.photo)
    ImageView imgItemPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        setupUI();
        setupListeners();
    }

    @Override
    public void setupUI() {
        imgItemPhoto.setImageResource(R.drawable.item_afghani_chicken);
    }

    @Override
    public void setupListeners() {

    }
}
