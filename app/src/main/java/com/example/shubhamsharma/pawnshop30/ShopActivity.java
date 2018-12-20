package com.example.shubhamsharma.pawnshop30;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.shubhamsharma.pawnshop30.RequestHandler.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ShopActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    SectionsPagerAdapter mSectionsPagerAdapter;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager mViewPager;
    final int[] ICONS = new int[] {
            R.drawable.cse_dept,
            R.drawable.etrx_dept,
            R.drawable.mech_dept,
            R.drawable.others
    };
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_navigation);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
       // Toast.makeText(this, String.valueOf("Splash="+getIntent().getIntExtra("User",12)), Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, String.valueOf("Login="+getIntent().getIntExtra("user_id",12)), Toast.LENGTH_SHORT).show();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Toast.makeText(this, String.valueOf(user_id), Toast.LENGTH_SHORT).show();

        pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        //myview start

        TextView mytext=(TextView) findViewById(R.id.mytext);
        Typeface face = Typeface.createFromAsset(getAssets(),"Billabong.ttf");
        mytext.setTypeface(face);
        int limit = (mSectionsPagerAdapter.getCount() > 1 ? mSectionsPagerAdapter.getCount() - 1 : 1);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(limit);
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
            tabLayout.newTab();//.setText(mSectionsPagerAdapter.getPageTitle(i));
        }
        tabLayout.getTabAt(0).setIcon(ICONS[0]);
        tabLayout.getTabAt(1).setIcon(ICONS[1]);
        tabLayout.getTabAt(2).setIcon(ICONS[2]);
        tabLayout.getTabAt(3).setIcon(ICONS[3]);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item =menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)item.getActionView();


        return true;
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
                intent = new Intent(ShopActivity.this, HomePageActivity.class);
                startActivity(intent);
                break;
            case R.id.user_deals:
                intent = new Intent(ShopActivity.this, User_activity_sell.class);
                startActivity(intent);
                break;

            case R.id.user_sell:
                intent = new Intent(ShopActivity.this, CameraActivity.class);
                startActivity(intent);
                break;

            case R.id.mywishlist:
                intent = new Intent(ShopActivity.this, Wishlist.class);
                startActivity(intent);
                break;
            case R.id.user_logout:
                editor.clear();
                editor.commit();
                intent = new Intent(ShopActivity.this, Interactive_login_register.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position){
                case 0:
                    fragment = new ProductViewFragment_CSE();
                    break;
                case 1:
                    fragment = new ProductViewFragment_ETRX();
                    break;
                case 2:
                    fragment = new ProductViewFragment_MECH();
                    break;
                case 3:
                    fragment = new ProductViewFragment_OTHERS();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }



}