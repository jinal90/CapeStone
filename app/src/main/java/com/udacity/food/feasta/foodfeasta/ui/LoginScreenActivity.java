package com.udacity.food.feasta.foodfeasta.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.helper.Utility;
import com.udacity.food.feasta.foodfeasta.helper.session.SessionFactory;
import com.udacity.food.feasta.foodfeasta.restaurant.customer.activity.LandingPageActivityCustomer;
import com.udacity.food.feasta.foodfeasta.restaurant.manager.activity.LandingPageActivityManager;

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
    @BindView(R.id.tableNameSpinner)
    Spinner tableNameSpinner;
    @BindView(R.id.activity_main)
    RelativeLayout rlMainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        ButterKnife.bind(this);

        MobileAds.initialize(getApplicationContext(), getString(R.string.ad_unit_app_id));

        setupUI();
        setupListeners();
    }

    @Override
    public void setupUI() {

        edtInputName.setVisibility(View.GONE);
        tableNameSpinner.setVisibility(View.GONE);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tableNameArray, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        tableNameSpinner.setAdapter(adapter);

        tableNameSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*TextView tmpView = (TextView) view.findViewById(android.R.id.text1);
                tmpView.setTextColor(Color.WHITE);*/
                SessionFactory.getInstance().setSelectedTable(getResources().getStringArray(R.array.tableNameArray)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        tableNameSpinner.setSelection(1);
        tableNameSpinner.setSelection(0);
        tableNameSpinner.setSelected(true);
        adapter.notifyDataSetChanged();

        showAdd();
    }


    @Override
    public void setupListeners() {

        edtInputName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtInputName.getWindowToken(), 0);
                }
            }
        });
    }

    @OnClick({R.id.btnSubmit, R.id.activity_main})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSubmit:
                if (radioCustomer.isChecked()) {
                    if (TextUtils.isEmpty(SessionFactory.getInstance().getSelectedTable())) {
                        SessionFactory.getInstance()
                                .setSelectedTable(getResources().getStringArray(R.array.tableNameArray)[0]);
                    }
                    Intent intent = new Intent(this, LandingPageActivityCustomer.class);
                    startActivity(intent);
                } else if (radioManager.isChecked()) {

                    if (edtInputName.getText().toString() != null
                            && !TextUtils.isEmpty(edtInputName.getText().toString())
                            && Constants.MANAGER_UNIQUE_USERNAME.equalsIgnoreCase(edtInputName.getText().toString())) {
                        Intent intent = new Intent(this, LandingPageActivityManager.class);
                        startActivity(intent);
                    } else {
                        Utility.showSnackbar(rlMainContainer, getString(R.string.message_invalid_username));
                    }


                } else {
                    Utility.showSnackbar(rlMainContainer, getString(R.string.message_select_usertype));
                }

                break;

            case R.id.activity_main:

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;

            default:
                break;
        }


    }

    @OnCheckedChanged({R.id.radioCustomer, R.id.radioManager})
    public void onRadioCheckedChanged(CompoundButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.radioCustomer:
                if (isChecked) {
                    edtInputName.setVisibility(View.GONE);
                    tableNameSpinner.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.radioManager:
                if (isChecked) {
                    edtInputName.setVisibility(View.VISIBLE);
                    tableNameSpinner.setVisibility(View.GONE);
                }
                break;

            default:

                break;
        }
    }

    public void showAdd() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
