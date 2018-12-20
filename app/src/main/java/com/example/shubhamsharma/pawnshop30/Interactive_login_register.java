package com.example.shubhamsharma.pawnshop30;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yoga on 26-03-2017.
 */

public class Interactive_login_register extends AppCompatActivity {
    SectionsPagerAdapter mSectionsPagerAdapter;
    TabLayout tabLayout;
    ViewPager mViewPager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interactive_login);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
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
        for (int i = 0; i <mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
        /* switch (i)
         {   case 0: tabLayout.newTab().setIcon(ICONS[0]);
                 break;
             case 1: tabLayout.newTab().setIcon(ICONS[1]);
                 break;
         }*/
            tabLayout.newTab();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new Interactive_login();
                    break;
                case 1:
                    fragment = new Interactive_register();
                    break;
            }
            return fragment;
        }


        @Override
        public int getCount() {
            return 2;
        }
    }

  /*  public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return getString(R.string.title_login).toUpperCase(l);
            case 1:
                return getString(R.string.title_register).toUpperCase(l);

        }
        return null;
    }*/
}
