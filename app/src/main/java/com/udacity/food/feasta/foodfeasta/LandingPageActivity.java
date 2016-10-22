package com.udacity.food.feasta.foodfeasta;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import com.udacity.food.feasta.foodfeasta.ui.MenuCursorAdapter;
import com.udacity.food.feasta.foodfeasta.ui.MenuFragment;
import com.udacity.food.feasta.foodfeasta.ui.ViewPagerAdapter;
import com.udacity.food.feasta.foodfeasta.ui.dummy.DummyContent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LandingPageActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MenuFragment.OnListFragmentInteractionListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    private MenuFragment mMenuFragment;
    private MediaPlayer mp;
    private static final int LOADER_SEARCH_RESULTS = 1;
    private MenuCursorAdapter adapter;

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

        // start loader
        this.getSupportLoaderManager().restartLoader(LOADER_SEARCH_RESULTS, null, this);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id)
        {
            case LOADER_SEARCH_RESULTS:

                final Uri uri = Uri.parse("https://myfirstfirebase-2c835.firebaseio.com/fooditem.json");
                return new CursorLoader(this, uri, null, null, null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId())
        {
            case LOADER_SEARCH_RESULTS:


                System.out.println("it came here onloadfinish");
                //this.adapter.swapCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId())
        {
            case LOADER_SEARCH_RESULTS:

                //this.adapter.swapCursor(null);
                break;
        }
    }
}
