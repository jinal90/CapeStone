package com.udacity.food.feasta.foodfeasta;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.udacity.food.feasta.foodfeasta.ui.BaseActivity;
import com.udacity.food.feasta.foodfeasta.ui.DetailViewActivity;
import com.udacity.food.feasta.foodfeasta.ui.MenuFragment;
import com.udacity.food.feasta.foodfeasta.ui.ViewPagerAdapter;
import com.udacity.food.feasta.foodfeasta.ui.dummy.DummyContent;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LandingPageActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MenuFragment.OnListFragmentInteractionListener {

    private MenuFragment mMenuFragment;
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
    FloatingActionButton fabFeedback;
    @BindView(R.id.fabWater)
    FloatingActionButton fabWater;
    @BindView(R.id.fabWaiter)
    FloatingActionButton fabWaiter;


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

    public void loadFrament() {
        mMenuFragment = MenuFragment.newInstance(1);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.rlFragmentContainer, mMenuFragment).commit();
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
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

        Intent detailViewIntent = new Intent(this, DetailViewActivity.class);
        startActivity(detailViewIntent);
    }

    @Override
    public void setupUI() {
        setSupportActionBar(toolbar);

        //loadFrament();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        setupViewPager(mViewPager);

        mTabs = (TabLayout) findViewById(R.id.tabs);
        mTabs.setupWithViewPager(mViewPager);

        MenuFetchingAsyncTask fetchingAsyncTask = new MenuFetchingAsyncTask();
        fetchingAsyncTask.execute();

    }

    @Override
    public void setupListeners() {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MenuFragment.newInstance(1), "Starters");
        adapter.addFragment(MenuFragment.newInstance(1), "Main Course");
        adapter.addFragment(MenuFragment.newInstance(1), "Desert");
        viewPager.setAdapter(adapter);
    }

    public class MenuFetchingAsyncTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            try {
                String response = null;
                URL url = new URL("https://myfirstfirebase-2c835.firebaseio.com/fooditem.json");
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

                System.out.println("json -- " + response);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
}
