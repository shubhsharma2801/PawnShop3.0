package com.example.shubhamsharma.pawnshop30;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.shubhamsharma.pawnshop30.RequestHandler.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener, NavigationView.OnNavigationItemSelectedListener {
    //NavigationBaseAdapter adapter;
    private com.example.shubhamsharma.pawnshop30.SliderLayout mDemoSlider;
    SearchView searchView;
    private RecyclerView recyclerView;
    private CardViewAdapter mAdapter;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String gtjson;

    ArrayList<ItemObject> items=new ArrayList<>();

    int user_id;
    String pin;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_display);
        pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();
       // SharedPreferences pre = getApplicationContext().getSharedPreferences("pre", MODE_PRIVATE);

       // int   id=pre.getInt("PostalCode",100);
//        Toast.makeText(this, pre.getInt("PostalCode",100), Toast.LENGTH_SHORT).show();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        new Task().execute();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mytext=(TextView) findViewById(R.id.mytext);
        Typeface face = Typeface.createFromAsset(getAssets(),"Billabong.ttf");
        mytext.setTypeface(face);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        initCollapsingToolbar();

        GridView gridView= (GridView) findViewById(R.id.gridview);
        mDemoSlider = (com.example.shubhamsharma.pawnshop30.SliderLayout) findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Computer Department",R.drawable.cse_slide_image);
        file_maps.put("Electronics Department",R.drawable.electronis_slide_image);
        file_maps.put("Mechanical Department",R.drawable.mech_slide_image);



        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);


            textSliderView.bundle(new Bundle());
            // textSliderView.getBundle().putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mDemoSlider.setPresetIndicator(com.example.shubhamsharma.pawnshop30.SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
    private class Task extends AsyncTask<String,String,String> {
        View view;
        int product_id;


        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2,1,false));
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
            } else {
                linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
            }
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getBaseContext(), 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new HomePageActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected String doInBackground(String... strings) {
            SharedPreferences pre = getApplicationContext().getSharedPreferences("pre", MODE_PRIVATE);

         int   id=pre.getInt("PostalCode",100);
            HashMap<String, String> params = new HashMap<>();

//Toast.makeText(HomePageActivity.this, String.valueOf(getIntent().getIntExtra("PinCode",100)), Toast.LENGTH_SHORT).show();
            params.put("pincode", String.valueOf(id));
            RequestHandler rh=new RequestHandler();

            gtjson= rh.sendPostRequest("http://192.168.43.181/olx/rv_home.php",params);
            //Toast.makeText(HomePageActivity.this, gtjson, Toast.LENGTH_SHORT).show();
            //return gtjson;
            try {
                String jsonParser=gtjson;
                JSONObject json=new JSONObject(jsonParser);
                JSONArray result=json.getJSONArray("result");

                for(int i=0;i<result.length();i++)
                {   //ItemObject home=new ItemObject();
                    JSONObject c1 = result.getJSONObject(i++);
                    product_id = c1.getInt("productid");
                    System.out.println("id:"+product_id);

                    JSONObject c4 = result.getJSONObject(i++);
                    String productName = c4.getString("name");

                    System.out.println("name:"+productName);

                    JSONObject c2 = result.getJSONObject(i++);
                    int productPrice = c2.getInt("price");

                    System.out.println("path:"+productPrice);

                    JSONObject c3= result.getJSONObject(i++);
                    int views = c3.getInt("views");
                    System.out.println("price:"+views);

                    JSONObject c5= result.getJSONObject(i);
                    String productImage = c5.getString("path");
                    System.out.println("catagory:"+productImage);
                    ItemObject home= new ItemObject(productImage,productName,productPrice,views,product_id);
                    items.add(home);
                    recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
                    mAdapter = new CardViewAdapter(items,HomePageActivity.this);
                    //cardView = (CardView) findViewById(R.id.cardList);





                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return gtjson;
        }
    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            searchView.setIconified(true);

        }
        else {
            //setContentView(R.layout.home_page);
            super.onBackPressed();
        }
    }
/**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
       final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
      //  appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();

                }
                if (scrollRange + verticalOffset == 0 ) {
                    collapsingToolbar.setTitle(" ");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)

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
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        /*
Integer page_no=mDemoSlider.getCurrentPosition();
    if(page_no==0)
    {
        mDemoSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText("CSE",2000, 1000);
            }
        });

    }*/
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
            }

    @Override
    public void onPageScrollStateChanged(int state) {}

    //NAVIGATION Drawer
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();
        switch (id) {

            case R.id.shop:
                intent = new Intent(HomePageActivity.this, ShopActivity.class);
                startActivity(intent);
                break;
            case R.id.user_deals:
                intent = new Intent(HomePageActivity.this, User_activity_sell.class);
                startActivity(intent);
                break;

            case R.id.user_sell:
                intent = new Intent(HomePageActivity.this, CameraActivity.class);
                startActivity(intent);
                break;

            case R.id.mywishlist:
                intent = new Intent(HomePageActivity.this, Wishlist.class);
                startActivity(intent);
                break;
            case R.id.user_logout:
                editor.clear();
                editor.commit();
                intent = new Intent(HomePageActivity.this, Interactive_login_register.class);
                startActivity(intent);
                break;

            default:
                break;
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        String username=pref.getString("UserName","shubham");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        TextView user_name=(TextView) findViewById(R.id.user_name);
        user_name.setText(username);

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
         searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(HomePageActivity.this,SearchActivity.class);
                intent.putExtra("value",0);
                intent.putExtra("search",query);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

       // searchView.clearFocus();
        return super.onCreateOptionsMenu(menu);
    }


}
