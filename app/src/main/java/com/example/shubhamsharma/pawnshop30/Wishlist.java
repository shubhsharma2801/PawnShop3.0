package com.example.shubhamsharma.pawnshop30;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubhamsharma.pawnshop30.RequestHandler.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static java.security.AccessController.getContext;

/**
 * Created by yoga on 10-04-2017.
 */

public class Wishlist extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    String gtjson;

    ArrayList<ItemObject> li=new ArrayList<>();
    private ArrayList<FeedProperties_wishlist> os_versions;
    private CardView cardView;
    private RecyclerView recyclerView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int id;
    private CardViewAdapter_wishlist mAdapter;
    CartHandler ch =new CartHandler(this);
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_navigation_wishlist);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);

        id=pref.getInt("UserId",1000);
        pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
        li=ch.getAllShops(String.valueOf(id));
        initContrls();

       // Toast.makeText(this, String.valueOf(getIntent().getIntExtra("product_id",10000)), Toast.LENGTH_SHORT).show();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mytext = (TextView) findViewById(R.id.txt);
        Typeface face = Typeface.createFromAsset(getAssets(), "Billabong.ttf");
        mytext.setTypeface(face);

        //navigation view starts
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //navigation view ends

   public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //setContentView(R.layout.wishlist);
            super.onBackPressed();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
   private void initContrls()
    {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        cardView = (CardView) findViewById(R.id.card5);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);




       // os_versions = new ArrayList<>();



        recyclerView.setHasFixedSize(true);
        //Grid View
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2,1,false));
        //StaggeredGridView
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
        // create an Object for Adapter
        mAdapter = new CardViewAdapter_wishlist(li,Wishlist.this);

        // set the adapter object to the Recyclerview
        recyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        } else {
            linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        }
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getBaseContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new Wishlist.GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Intent intent;
        int id = item.getItemId();
        switch (id) {
            case R.id.shop:
                intent = new Intent(Wishlist.this, ShopActivity.class);
                startActivity(intent);
                break;
            case R.id.my_home:
                intent = new Intent(Wishlist.this, HomePageActivity.class);
                startActivity(intent);
                break;
            case R.id.user_deals:
                intent = new Intent(Wishlist.this, User_activity_sell.class);
                startActivity(intent);
                break;

            case R.id.user_sell:
                intent = new Intent(Wishlist.this, CameraActivity.class);
                startActivity(intent);
                break;

            case R.id.mywishlist:
                intent = new Intent(Wishlist.this, Wishlist.class);
                startActivity(intent);
                break;
            case R.id.user_logout:
                editor.clear();
                editor.commit();
                intent = new Intent(Wishlist.this, Interactive_login_register.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}

