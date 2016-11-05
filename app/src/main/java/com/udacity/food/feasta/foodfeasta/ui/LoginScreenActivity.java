package com.udacity.food.feasta.foodfeasta.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
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
    }

    @Override
    public void setupListeners() {

    }

    @OnClick(R.id.btnSubmit)
    public void onClick(View view) {

        if (radioCustomer.isChecked()) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, LandingPageActivityCustomer.class);
            startActivity(intent);
        } else if (radioManager.isChecked()) {

            if (edtInputName.getText().toString() != null
                    && !TextUtils.isEmpty(edtInputName.getText().toString())
                    && Constants.MANAGER_UNIQUE_USERNAME.equalsIgnoreCase(edtInputName.getText().toString())) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, LandingPageActivityManager.class);
                startActivity(intent);
            } else {
                //Toast.makeText(this, "Kindly enter valid Manager username", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar
                        .make(rlMainContainer, "Kindly enter valid Manager username", Snackbar.LENGTH_LONG);

                snackbar.show();
            }


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
