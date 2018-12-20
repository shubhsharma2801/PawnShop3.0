package com.example.shubhamsharma.pawnshop30;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Locale;


public class User_activity_sell extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    SectionsPagerAdapter mSectionsPagerAdapter;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager mViewPager;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_navigation);
        pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//myview start
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mytext=(TextView) findViewById(R.id.mytext);
        //set user_name here
        Typeface face = Typeface.createFromAsset(getAssets(),"Billabong.ttf");
        mytext.setTypeface(face);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);//setting tab over viewpager
        //Implementing tab selected listener over tablayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());//setting current selected item over viewpager

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i <=mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            tabLayout.newTab().setText(mSectionsPagerAdapter.getPageTitle(i));
        }
//myview end

        //navigation view starts
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //navigation view ends
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //setContentView(R.layout.activity_main);
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        int id = item.getItemId();
        switch (id) {

            case R.id.my_home:
                intent = new Intent(User_activity_sell.this, HomePageActivity.class);
                startActivity(intent);
                break;
            case R.id.shop:
                intent = new Intent(User_activity_sell.this, ShopActivity.class);
                startActivity(intent);
                break;

            case R.id.user_sell:
                intent = new Intent(User_activity_sell.this, CameraActivity.class);
                startActivity(intent);
                break;

            case R.id.mywishlist:
                intent = new Intent(User_activity_sell.this, Wishlist.class);
                startActivity(intent);
                break;
            case R.id.user_logout:
                editor.clear();
                editor.commit();
                intent = new Intent(User_activity_sell.this, Interactive_login_register.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
   /*/**
         * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
         * one of the sections/tabs/pages.
         */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position){
                case 0:
                    fragment = new UserAdsViewFragment();
                    break;
                case 1:
                    fragment = new Sold_product_fragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

       @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section5).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section6).toUpperCase(l);
                }
            return null;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        return super.onCreateOptionsMenu(menu);
    }
}