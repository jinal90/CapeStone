package com.udacity.food.feasta.foodfeasta.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.udacity.food.feasta.foodfeasta.LandingPageActivity;
import com.udacity.food.feasta.foodfeasta.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class LoginScreenActivity extends BaseActivity {


    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.radioGroupUserType)
    RadioGroup radioGroupUserType;
    @BindView(R.id.radioCustomer)
    RadioButton radioCustomer;
    @BindView(R.id.radioManager)
    RadioButton radioManager;
    @BindView(R.id.edtName)
    EditText edtInputName;
    @BindView(R.id.edtTableNo)
    EditText edtInputTableNo;

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

        edtInputName.setVisibility(View.GONE);
        edtInputTableNo.setVisibility(View.GONE);
    }

    @Override
    public void setupListeners() {

    }

    @OnClick(R.id.btnSubmit)
    public void onClick(View view) {

        if (radioCustomer.isChecked() || radioManager.isChecked()) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, LandingPageActivity.class);
            startActivity(intent);
        } else {

            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }

    }

    @OnCheckedChanged({R.id.radioCustomer, R.id.radioManager})
    public void onRadioCheckedChanged(CompoundButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.radioCustomer:
                if (isChecked) {
                    edtInputName.setVisibility(View.GONE);
                    edtInputTableNo.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radioManager:
                if (isChecked) {
                    edtInputName.setVisibility(View.VISIBLE);
                    edtInputTableNo.setVisibility(View.GONE);
                }
                break;

            default:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
