package com.example.shubhamsharma.pawnshop30;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shubhamsharma.pawnshop30.RequestHandler.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.shubhamsharma.pawnshop30.R.id.container;
import static com.example.shubhamsharma.pawnshop30.R.id.name;
import static com.example.shubhamsharma.pawnshop30.R.id.visible;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    SearchView searchView;
    String gtjson;


    ArrayList<ItemObject> li=new ArrayList<>();
    private ArrayList<FeedProperties_wishlist> os_versions;
    private CardView cardView;
    private RecyclerView recyclerView;
    TextView mytext;
    int id,pincode;

    List<ItemObject> items=new ArrayList<>();
    GridView gridview;
    private CardViewAdapter_wishlist mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mytext = (TextView) findViewById(R.id.mytext);
        Typeface face = Typeface.createFromAsset(getAssets(), "Billabong.ttf");
        mytext.setTypeface(face);
        String query=getIntent().getStringExtra("search");
        SharedPreferences pre = getBaseContext().getSharedPreferences("pre", MODE_PRIVATE);
         pincode=pre.getInt("PostalCode",100);
        //View view = inflater.inflate(R.layout.product_view, container, false);
         id=getIntent().getIntExtra("value",1000);
        if (id==0){
            String url="http://192.168.43.181/olx/search_others.php";

            new Task(query,pincode,url).execute();
        }else if (id==1){
            String url="http://192.168.43.181/olx/search_cse.php";

            new Task(query,pincode,url).execute();

        }else if (id==2){
            String url="http://192.168.43.181/olx/search_electronics.php";

            new Task(query,pincode,url).execute();

        }else if (id==3){
            String url="http://192.168.43.181/olx/search_mechanical.php";

            new Task(query,pincode,url).execute();

        }



    }


        class Task extends AsyncTask<String,String,String> {
            String query;
            int product_id;
            int pincode;
            String url;

            public Task(String v,int i,String q) {
                this.query=v;
                this.pincode=i;
                this.url=q;

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                gridview = (GridView) findViewById(R.id.gridview);
                CustomAdapter customAdapter = new CustomAdapter(getBaseContext(), items);
                gridview.setAdapter(customAdapter);
                //items.clear();
                if (gtjson==null){
                    Toast.makeText(SearchActivity.this, "Items not found", Toast.LENGTH_LONG).show();
                }

            }


            @Override
            protected String doInBackground(String... strings) {
                HashMap<String, String> params = new HashMap<>();

//            Toast.makeText(HomePageActivity.this, String.valueOf(getIntent().getIntExtra("PinCode",100)), Toast.LENGTH_SHORT).show();
                params.put("pincode", String.valueOf(pincode));
                params.put("name",query);
                RequestHandler rh=new RequestHandler();
                gtjson= rh.sendPostRequest(url,params);
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
                        //data.add(home);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return gtjson;
            }
        }
    public void onBackPressed() {
        startActivity(new Intent(SearchActivity.this,HomePageActivity.class));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                items.clear();
                if (id==0){
                    String url="http://192.168.43.181/olx/search_others.php";

                    new Task(query,pincode,url).execute();
                }else if (id==1){
                    String url="http://192.168.43.181/olx/search_cse.php";

                    new Task(query,pincode,url).execute();

                }else if (id==2){
                    String url="http://192.168.43.181/olx/search_electronics.php";

                    new Task(query,pincode,url).execute();

                }else if (id==3){
                    String url="http://192.168.43.181/olx/search_mechanical.php";

                    new Task(query,pincode,url).execute();

                }

                //Intent intent=new Intent(SearchActivity.this, SearchActivity.class);
                //startActivity(intent);

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
