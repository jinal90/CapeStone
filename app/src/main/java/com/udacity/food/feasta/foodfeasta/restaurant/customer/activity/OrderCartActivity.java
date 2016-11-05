package com.udacity.food.feasta.foodfeasta.restaurant.customer.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.database.TableOrderDataSource;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.helper.Utility;
import com.udacity.food.feasta.foodfeasta.helper.session.SessionFactory;
import com.udacity.food.feasta.foodfeasta.model.TableOrder;
import com.udacity.food.feasta.foodfeasta.restaurant.customer.fragment.CurrentOrderFragment;

import java.util.concurrent.Callable;

public class OrderCartActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private AlertDialog dialog;
    private GoogleApiClient mGoogleApiClient;
    private Message mActiveMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CurrentOrderFragment fragment = CurrentOrderFragment.newInstance(Constants.CURRENT_TABLE);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();

        findViewById(R.id.btnGenerateCheque).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                dialog = Utility.showTwoButtonDialog(OrderCartActivity.this,
                        getString(R.string.confirm_title),
                        getString(R.string.cheque_confirm_message),
                        getString(R.string.btn_OK),
                        new Callable() {
                            @Override
                            public Object call() throws Exception {

                                TableOrder message = new TableOrder();
                                //message.setTableName("Table 1");
                                message.setTableName(SessionFactory.getInstance()
                                        .getSelectedTable(OrderCartActivity.this));
                                message.setFoodItemName(Constants.CLEAR_TABLE);
                                Gson gson = new Gson();
                                String msgJson = gson.toJson(message);
                                publish(msgJson);

                                TableOrderDataSource dataSource =
                                        new TableOrderDataSource(OrderCartActivity.this);
                                dataSource.open();
                                dataSource.deleteCurrentOrder();
                                dataSource.close();

                                showGoodByeDialog();
                                return null;
                            }
                        },
                        getString(R.string.btn_cancel),
                        null);
                dialog.show();

            }
        });
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .build();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void showGoodByeDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Thank you for visiting Food Feasta. Hope you enjoyed our services.");

        builder.setPositiveButton(getString(R.string.btn_OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
}
