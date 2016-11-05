package com.udacity.food.feasta.foodfeasta.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.google.gson.Gson;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.database.MenuDataSource;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.helper.Utility;
import com.udacity.food.feasta.foodfeasta.helper.session.SessionFactory;
import com.udacity.food.feasta.foodfeasta.model.FoodMenu;
import com.udacity.food.feasta.foodfeasta.restaurant.customer.activity.LandingPageActivityCustomer;
import com.udacity.food.feasta.foodfeasta.restaurant.manager.activity.LandingPageActivityManager;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
    @BindView(R.id.rlMainContent)
    RelativeLayout rlDataContainer;
    @BindView(R.id.rlErrorView)
    RelativeLayout rlErrorView;
    @BindView(R.id.rlProgressIndicator)
    RelativeLayout rlProgressIndicator;

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
        getSupportActionBar().setTitle(getString(R.string.title_activity_setup));

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

        MenuFetchingAsyncTask fetchingAsyncTask
                = new MenuFetchingAsyncTask();
        fetchingAsyncTask.execute();

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


    public void showProgressIndicator() {

        rlProgressIndicator.setVisibility(View.VISIBLE);
        rlErrorView.setVisibility(View.GONE);
        rlDataContainer.setVisibility(View.GONE);

    }

    public void showErrorView() {
        rlProgressIndicator.setVisibility(View.GONE);
        rlErrorView.setVisibility(View.VISIBLE);
        rlDataContainer.setVisibility(View.GONE);
    }

    public void showContent() {
        rlProgressIndicator.setVisibility(View.GONE);
        rlErrorView.setVisibility(View.GONE);
        rlDataContainer.setVisibility(View.VISIBLE);
    }

    public class MenuFetchingAsyncTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressIndicator();
        }

        @Override
        protected Integer doInBackground(String... params) {

            if (Utility.isOnline(LoginScreenActivity.this)) {
                HttpURLConnection urlConnection = null;
                try {
                    String response = null;
                    URL url = new URL(Constants.MENU_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(30000);
                    InputStream in = urlConnection.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    byte[] buff = new byte[1024];
                    int count;
                    while ((count = in.read(buff)) > 0) {
                        sb.append(new String(buff, 0, count));
                    }
                    response = sb.toString();

                    if (!TextUtils.isEmpty(response)) {
                        //Utility.saveStringDataInPref(LandingPageActivityCustomer.this, "MenuData", response);
                        MenuDataSource menuDataSource = new MenuDataSource(LoginScreenActivity.this);
                        menuDataSource.open();

                        menuDataSource.deleteAllItems();
                        Gson gson = new Gson();
                        FoodMenu fullMenu = gson.fromJson(response, FoodMenu.class);
                        if (fullMenu != null && fullMenu.getFooditem() != null
                                && fullMenu.getFooditem().size() > 0) {
                            for (int i = 0; i < fullMenu.getFooditem().size(); i++) {

                                menuDataSource.createItem(fullMenu.getFooditem().get(i));
                            }
                        }
                        menuDataSource.close();
                        return Constants.SUCCESS;
                    }

                    System.out.println("json -- " + response);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } else {
                return Constants.NETWORK_ERROR;
            }

            return Constants.FAILURE;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            switch (result) {
                case Constants.SUCCESS:
                    showContent();
                    break;
                case Constants.FAILURE:
                    showErrorView();
                    break;
                case Constants.NETWORK_ERROR:
                    showErrorView();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
