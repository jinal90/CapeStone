package com.udacity.food.feasta.foodfeasta.restaurant.manager.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionMenu;
import com.udacity.food.feasta.foodfeasta.R;
import com.udacity.food.feasta.foodfeasta.helper.Constants;
import com.udacity.food.feasta.foodfeasta.helper.Utility;
import com.udacity.food.feasta.foodfeasta.model.Fooditem;
import com.udacity.food.feasta.foodfeasta.restaurant.manager.fragment.TableOrderFragment;
import com.udacity.food.feasta.foodfeasta.ui.ViewPagerAdapter;
import com.udacity.food.feasta.foodfeasta.ui.BaseActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LandingPageActivityManager extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TableOrderFragment mTableOrderFragment;
    private MediaPlayer mp;

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
    @BindView(R.id.fabFeedback)
    com.github.clans.fab.FloatingActionButton fabFeedback;
    @BindView(R.id.fabWater)
    com.github.clans.fab.FloatingActionButton fabWater;
    @BindView(R.id.fabWaiter)
    com.github.clans.fab.FloatingActionButton fabWaiter;
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
    }

    @OnClick({R.id.fabWaiter, R.id.fabWater, R.id.fabFeedback})
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
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.fabWater:

                break;
            case R.id.fabFeedback:

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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

        LandingPageActivityManager.MenuFetchingAsyncTask fetchingAsyncTask = new LandingPageActivityManager.MenuFetchingAsyncTask();
        fetchingAsyncTask.execute();

    }

    @Override
    public void setupListeners() {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TableOrderFragment.newInstance(11), "Starters");
        adapter.addFragment(TableOrderFragment.newInstance(12), "Main Course");
        adapter.addFragment(TableOrderFragment.newInstance(13), "Desert");
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

    public class MenuFetchingAsyncTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressIndicator();
        }

        @Override
        protected Integer doInBackground(String... params) {

            if (Utility.isOnline(LandingPageActivityManager.this)) {
                HttpURLConnection urlConnection = null;
                try {
                    String response = null;
                    URL url = new URL("https://myfirstfirebase-2c835.firebaseio.com/foodmenu.json");
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
                        Utility.saveStringDataInPref(LandingPageActivityManager.this, "MenuData", response);
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
                default:
                    break;
            }
        }
    }
}
