package com.example.shubhamsharma.pawnshop30;

/**
 * Created by yoga on 17-03-2017.
 */


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.shubhamsharma.pawnshop30.RequestHandler.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProductViewFragment_ETRX extends Fragment {
    List<ItemObject> items=new ArrayList<>();
    String gtjson;
    GridView gridview;
    int id;


    public ProductViewFragment_ETRX() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_view, container, false);
        SharedPreferences pre = getContext().getSharedPreferences("pre", MODE_PRIVATE);
        setHasOptionsMenu(true);
        id=pre.getInt("PostalCode",100);



        new Task(view).execute();







        return view;
    }
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),SearchActivity.class);
                intent.putExtra("value",2);
                intent.putExtra("search",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private class Task extends AsyncTask<String,String,String> {
        View view;
        int product_id;
        public Task(View v) {
            this.view=v;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            gridview = (GridView) view.findViewById(R.id.gridview);
            CustomAdapter customAdapter = new CustomAdapter(getActivity(), items);
            gridview.setAdapter(customAdapter);
            /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent=new Intent(getActivity(),On_click_product.class);
                    intent.putExtra("product_id",product_id);
                    startActivity(intent);

                }
            });*/
        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String, String> params = new HashMap<>();

//            Toast.makeText(HomePageActivity.this, String.valueOf(getIntent().getIntExtra("PinCode",100)), Toast.LENGTH_SHORT).show();
            params.put("pincode", String.valueOf(id));
            RequestHandler rh=new RequestHandler();
            gtjson= rh.sendPostRequest("http://192.168.43.181/olx/rv_electronics.php",params);

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

}