package com.udacity.food.feasta.foodfeasta.restaurant.manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.gson.Gson;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.database.TableOrderDataSource;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.model.TableOrder;
import com.udacity.food.feasta.foodfeasta.restaurant.manager.fragment.TableOrderFragment;
import com.udacity.food.feasta.foodfeasta.ui.BaseActivity;
import com.udacity.food.feasta.foodfeasta.ui.ViewPagerAdapter;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingPageActivityManager extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private Message mActiveMessage;
    private MessageListener mMessageListener;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.rlErrorView)
    RelativeLayout rlErrorView;
    @BindView(R.id.rlProgressIndicator)
    RelativeLayout rlProgressIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_manager);

        ButterKnife.bind(this);

        setupUI();
        setupListeners();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .build();

        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                String messageAsString = new String(message.getContent());
                //Toast.makeText(LandingPageActivityManager.this, messageAsString, Toast.LENGTH_SHORT).show();

                if (!TextUtils.isEmpty(messageAsString)) {
                    try {
                        Gson gson = new Gson();
                        TableOrder orderJson = gson.fromJson(messageAsString, TableOrder.class);
                        if (orderJson != null) {

                            if (!TextUtils.isEmpty(orderJson.getTableName())
                                    && !TextUtils.isEmpty(orderJson.getFoodItemName())) {
                                if (Constants.CALL_WAITER.equalsIgnoreCase(orderJson.getFoodItemName())) {

                                    Toast.makeText(LandingPageActivityManager.this,
                                            String.format(Locale.ENGLISH,
                                                    getString(R.string.message_send_waiter),
                                                    orderJson.getTableName()),
                                            Toast.LENGTH_SHORT).show();

                                } else if (Constants.ORDER_WATER.equalsIgnoreCase(orderJson.getFoodItemName())) {
                                    Toast.makeText(LandingPageActivityManager.this,
                                            String.format(Locale.ENGLISH,
                                                    getString(R.string.message_send_drinking_water),
                                                    orderJson.getTableName()),
                                            Toast.LENGTH_SHORT).show();
                                }else if (Constants.CLEAR_TABLE.equalsIgnoreCase(orderJson.getFoodItemName())) {
                                    Toast.makeText(LandingPageActivityManager.this,
                                            String.format(Locale.ENGLISH,
                                                    getString(R.string.message_clear_table),
                                                    orderJson.getTableName()),
                                            Toast.LENGTH_SHORT).show();

                                    TableOrderDataSource dataSource =
                                            new TableOrderDataSource(LandingPageActivityManager.this);
                                    dataSource.open();
                                    dataSource.deleteOrder(orderJson.getTableName());
                                    dataSource.close();

                                    Intent intent = new Intent("foodfeasta.OrderReceiver");
                                    intent.putExtra("selectedTable", orderJson.getTableName());
                                    sendBroadcast(intent);
                                } else {
                                    Toast.makeText(LandingPageActivityManager.this,
                                            String.format(Locale.ENGLISH,
                                                    getString(R.string.message_new_order),
                                                    orderJson.getTableName(),
                                                    orderJson.getFoodItemName()),
                                            Toast.LENGTH_SHORT).show();
                                    TableOrderDataSource dataSource =
                                            new TableOrderDataSource(LandingPageActivityManager.this);
                                    dataSource.open();
                                    dataSource.createOrder(orderJson.getTableName(), orderJson.getFoodItemName());
                                    dataSource.close();

                                    Intent intent = new Intent("foodfeasta.OrderReceiver");
                                    intent.putExtra("selectedTable", orderJson.getTableName());
                                    sendBroadcast(intent);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onLost(Message message) {
                String messageAsString = new String(message.getContent());
            }
        };
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
            unsubscribe();
            mGoogleApiClient.disconnect();
        }
    }

    // Subscribe to receive messages.
    private void subscribe() {
        Nearby.Messages.subscribe(mGoogleApiClient, mMessageListener);
    }

    private void unsubscribe() {
        Nearby.Messages.unsubscribe(mGoogleApiClient, mMessageListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_availability) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setupUI() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        showContent();

    }

    @Override
    public void setupListeners() {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Iterator it = Constants.TABLE_MAP.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = (String) pair.getKey();
            Integer value = (Integer) pair.getValue();
            adapter.addFragment(TableOrderFragment.newInstance(value), key);
        }
        /*adapter.addFragment(TableOrderFragment.newInstance(11), "Starters");
        adapter.addFragment(TableOrderFragment.newInstance(12), "Main Course");
        adapter.addFragment(TableOrderFragment.newInstance(13), "Desert");*/
        viewPager.setAdapter(adapter);
    }

    public void showProgressIndicator() {

        rlProgressIndicator.setVisibility(View.VISIBLE);
        rlErrorView.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        mTabs.setVisibility(View.GONE);

    }

    public void showErrorView() {
        rlProgressIndicator.setVisibility(View.GONE);
        rlErrorView.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        mTabs.setVisibility(View.GONE);
    }

    public void showContent() {
        rlProgressIndicator.setVisibility(View.GONE);
        rlErrorView.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        mTabs.setVisibility(View.VISIBLE);
        setupViewPager(mViewPager);
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mTabs.setupWithViewPager(mViewPager);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        subscribe();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
