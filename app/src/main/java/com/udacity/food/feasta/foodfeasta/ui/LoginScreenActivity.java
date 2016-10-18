package com.udacity.food.feasta.foodfeasta.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.udacity.food.feasta.foodfeasta.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginScreenActivity extends BaseActivity {


    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ButterKnife.bind(this);

        setupUI();
        setupListeners();
    }

    @Override
    public void setupUI() {

    }

    @Override
    public void setupListeners() {

    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitClick() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
