package com.udacity.food.feasta.foodfeasta.restaurant.customer.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.helper.Utility;
import com.udacity.food.feasta.foodfeasta.helper.session.SessionFactory;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;
import com.udacity.food.feasta.foodfeasta.model.TableOrder;
import com.udacity.food.feasta.foodfeasta.restaurant.customer.fragment.MenuFragment;
import com.udacity.food.feasta.foodfeasta.ui.BaseActivity;
import com.udacity.food.feasta.foodfeasta.ui.ViewPagerAdapter;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LandingPageActivityCustomer extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MenuFragment.OnListFragmentInteractionListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private MediaPlayer mp;
    private GoogleApiClient mGoogleApiClient;
    private Message mActiveMessage;
    private AlertDialog dialog;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.floatingActionMenu)
    FloatingActionMenu mFloatingActionMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.fabWater)
    FloatingActionButton fabWater;
    @BindView(R.id.fabWaiter)
    FloatingActionButton fabWaiter;
    @BindView(R.id.rlErrorView)
    RelativeLayout rlErrorView;
    @BindView(R.id.rlProgressIndicator)
    RelativeLayout rlProgressIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        ButterKnife.bind(this);

        //mp = MediaPlayer.create(this, R.raw.door_bell);
        mp = new MediaPlayer();
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

    @OnClick({R.id.fabWaiter, R.id.fabWater})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fabWaiter:

                try {
                    //mp.start();
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    mp.reset();
                    AssetFileDescriptor afd;
                    afd = getAssets().openFd("door_bell.mp3");
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mp.prepare();
                    mp.start();

                    if (dialog != null && dialog.isShowing())
                        dialog.dismiss();

                    dialog = Utility.showTwoButtonDialog(this,
                            getString(R.string.confirm_title),
                            getString(R.string.call_waiter_confirm_message),
                            getString(R.string.btn_yes),
                            new Callable() {
                                @Override
                                public Object call() throws Exception {
                                    TableOrder message = new TableOrder();
                                    //message.setTableName("Table 1");
                                    message.setTableName(SessionFactory.getInstance().getSelectedTable(LandingPageActivityCustomer.this));
                                    message.setFoodItemName(Constants.CALL_WAITER);
                                    Gson gson = new Gson();
                                    String msgJson = gson.toJson(message);
                                    publish(msgJson);

                                    return null;
                                }
                            },
                            getString(R.string.btn_no),
                            null);
                    dialog.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.fabWater:
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                dialog = Utility.showTwoButtonDialog(this,
                        getString(R.string.confirm_title),
                        getString(R.string.get_water_confirm_message),
                        getString(R.string.btn_yes),
                        new Callable() {
                            @Override
                            public Object call() throws Exception {
                                TableOrder message = new TableOrder();
                                //message.setTableName("Table 1");
                                message.setTableName(SessionFactory.getInstance().getSelectedTable(LandingPageActivityCustomer.this));
                                message.setFoodItemName(Constants.ORDER_WATER);
                                Gson gson = new Gson();
                                String msgJson = gson.toJson(message);
                                publish(msgJson);
                                return null;
                            }
                        },
                        getString(R.string.btn_no),
                        null);
                dialog.show();

                break;
            default:

                break;

        }
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

        if (id == R.id.nav_showOrder) {
            // Handle the camera action

            Intent orderIntent = new Intent(this, OrderCartActivity.class);
            startActivity(orderIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Fooditem item) {

        Intent detailViewIntent = new Intent(this, DetailViewActivity.class);
        detailViewIntent.putExtra(Constants.SELECTED_FOOD_ITEM, item);
        startActivity(detailViewIntent);
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

        Iterator it = Constants.MENU_TYPE_HASHMAP.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = (String) pair.getKey();
            Integer value = (Integer) pair.getValue();
            adapter.addFragment(MenuFragment.newInstance(value), key);
        }
        /*adapter.addFragment(MenuFragment.newInstance(11), "Starters");
        adapter.addFragment(MenuFragment.newInstance(12), "Main Course");
        adapter.addFragment(MenuFragment.newInstance(13), "Desert");*/
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

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
