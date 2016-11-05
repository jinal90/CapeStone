package com.udacity.food.feasta.foodfeasta.restaurant.customer.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.helper.Utility;
import com.udacity.food.feasta.foodfeasta.helper.session.SessionFactory;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;
import com.udacity.food.feasta.foodfeasta.model.TableOrder;
import com.udacity.food.feasta.foodfeasta.ui.BaseActivity;

import java.util.Locale;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailViewActivity extends BaseActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.fab)
    FloatingActionButton fabAdd;
    @BindView(R.id.photo)
    ImageView imgItemPhoto;
    @BindView(R.id.tvItemDescription)
    TextView tvItemDescription;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Fooditem mFoodItem;
    private GoogleApiClient mGoogleApiClient;
    private Message mActiveMessage;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setupUI();
        setupListeners();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            unpublish();
            mGoogleApiClient.disconnect();
        }
    }

    public void publish(String message) {
        mActiveMessage = new Message(message.getBytes());
        Nearby.Messages.publish(mGoogleApiClient, mActiveMessage);
    }

    private void unpublish() {
        if (mActiveMessage != null && mGoogleApiClient.isConnected()) {
            Nearby.Messages.unpublish(mGoogleApiClient, mActiveMessage);
            mActiveMessage = null;
        }
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
                getSupportActionBar().setTitle(mFoodItem.getName());
            }

            if (!TextUtils.isEmpty(mFoodItem.getLong_desc())) {
                tvItemDescription.setText(mFoodItem.getLong_desc());
            }
        }


    }

    @Override
    public void setupListeners() {

    }

    @OnClick(R.id.fab)
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fab:

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                dialog = Utility.showTwoButtonDialog(this,
                        getString(R.string.order_confirm_title),
                        String.format(Locale.ENGLISH,
                                getString(R.string.order_confirm_message),
                                mFoodItem.getName(), mFoodItem.getPrice()),
                        getString(R.string.btn_OK),
                        new Callable() {
                            @Override
                            public Object call() throws Exception {
                                TableOrder message = new TableOrder();
                                //message.setTableName("Table 1");
                                message.setTableName(SessionFactory.getInstance().getSelectedTable(DetailViewActivity.this));
                                message.setFoodItemName(mFoodItem.getName());
                                Gson gson = new Gson();
                                String msgJson = gson.toJson(message);
                                publish(msgJson);
                                return null;
                            }
                        },
                        getString(R.string.btn_cancel),
                        null);
                dialog.show();

                break;
            default:

                break;

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
