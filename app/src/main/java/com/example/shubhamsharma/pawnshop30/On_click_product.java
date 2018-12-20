package com.example.shubhamsharma.pawnshop30;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shubhamsharma.pawnshop30.RequestHandler.RequestHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class On_click_product extends AppCompatActivity {
    TextView name, price, description, views, seller;
    int id, user_id;

    double jprice1;
    CartHandler ch = new CartHandler(this);
    String gtjson;
    ArrayList<ItemObject> li = new ArrayList<>();
    ImageView imagemain, imagev1, imagev2, imagev3, imagev4, wish;
    Button deal;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_click_product);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        //Toast.makeText(this, String.valueOf(getIntent().getIntExtra("product_id",50000)), Toast.LENGTH_SHORT).show();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        id = pref.getInt("UserId", 1000);
        views();
        //Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();
        retrieve();
        wish = (ImageView) findViewById(R.id.wishlist);
        name = (TextView) findViewById(R.id.name);
        price = (TextView) findViewById(R.id.price);
        description = (TextView) findViewById(R.id.description);
        views = (TextView) findViewById(R.id.views1);
        seller = (TextView) findViewById(R.id.Seller);
        imagemain = (ImageView) findViewById(R.id.main_image);
        imagev1 = (ImageView) findViewById(R.id.image1);
        imagev2 = (ImageView) findViewById(R.id.image2);
        imagev3 = (ImageView) findViewById(R.id.image3);
        imagev4 = (ImageView) findViewById(R.id.image4);
        deal = (Button) findViewById(R.id.deal);

    }

    private void views() {
        class LoginTask extends AsyncTask<String, String, String> {
            private String gtjson;
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... strings) {
                HashMap<String, String> params = new HashMap<>();
                params.put("username", String.valueOf(id));
                params.put("productid", String.valueOf(getIntent().getIntExtra("product_id", 50000)));


                RequestHandler rh = new RequestHandler();
                gtjson = rh.sendPostRequest("http://192.168.43.181/olx/views_increment.php", params);

                //gtjson = sendpost("http://192.168.43.181/Android/CRUD/login.php", params);


                return gtjson;
            }


        }

        LoginTask lt = new LoginTask();
        lt.execute();

    }


    private void retrieve() {
        class LoginTask extends AsyncTask<String, String, String> {
            private String gtjson;
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... strings) {
                HashMap<String, String> params = new HashMap<>();
                params.put("userid", String.valueOf(id));
                params.put("productid", String.valueOf(getIntent().getIntExtra("product_id", 50000)));

                RequestHandler rh = new RequestHandler();
                gtjson = rh.sendPostRequest("http://192.168.43.181/olx/product_details.php", params);
                //gtjson = sendpost("http://192.168.43.181/Android/CRUD/login.php", params);


                return gtjson;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    // Toast.makeText(DisplayCse.this, gtjson, Toast.LENGTH_SHORT).show();
                    //tv_dis.setText (gtjson);
                    String jsonParser = gtjson;
                    JSONObject json = new JSONObject(jsonParser);
                    JSONArray result = json.getJSONArray("result");
                    JSONObject c1 = result.getJSONObject(0);
                    String name1 = c1.getString("name");
                    JSONObject c2 = result.getJSONObject(1);
                    double price1 = c2.getDouble("price");
                    JSONObject c3 = result.getJSONObject(2);
                    String description1 = c3.getString("description");

                    JSONObject c5 = result.getJSONObject(3);
                    int views1 = c5.getInt("views");
                    JSONObject c6 = result.getJSONObject(4);
                    final String imagepath = c6.getString("path1");
                    JSONObject c7 = result.getJSONObject(5);
                    final String imagepath1 = c7.getString("path2");
                    JSONObject c8 = result.getJSONObject(6);
                    final String imagepath2 = c8.getString("path3");
                    JSONObject c9 = result.getJSONObject(7);
                    final String imagepath3 = c9.getString("path4");
                    JSONObject c10 = result.getJSONObject(8);
                    final int product = c10.getInt("product_id");
                    JSONObject c11 = result.getJSONObject(9);
                    final String sellername = c11.getString("sellername");
                    JSONObject c12 = result.getJSONObject(10);
                    final String selleradd = c12.getString("address");
                    JSONObject c13 = result.getJSONObject(11);
                    final int phone = c13.getInt("phone");
                    name.setText(name1);
                    price.setText("Rs:" + String.valueOf(price1));
                    description.setText(String.valueOf(description1));
                    views.setText(String.valueOf(views1) + " views");
                    seller.setText("Name:- " + sellername + "\nAddress:- " + selleradd);
                    Picasso.with(On_click_product.this).load(imagepath)
                            .placeholder(R.drawable.onload)
                            .error(R.drawable.onload)
                            .into(imagemain);
                    Picasso.with(On_click_product.this).load(imagepath)
                            .placeholder(R.drawable.onload)
                            .error(R.drawable.onload)
                            .into(imagev1);
                    Picasso.with(On_click_product.this).load(imagepath1)
                            .placeholder(R.drawable.onload)
                            .error(R.drawable.onload)
                            .into(imagev2);
                    Picasso.with(On_click_product.this).load(imagepath2)
                            .placeholder(R.drawable.onload)
                            .error(R.drawable.onload)
                            .into(imagev3);
                    Picasso.with(On_click_product.this).load(imagepath3)
                            .placeholder(R.drawable.onload)
                            .error(R.drawable.onload)
                            .into(imagev4);

                    jprice1 = price1;
                    wish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int s = ch.getState(String.valueOf(product), String.valueOf(id));
                            //Toast.makeText(On_click_product.this, String.valueOf(s), Toast.LENGTH_SHORT).show();
                            if (s == 1) {

                                Toast.makeText(On_click_product.this, "Item already in wishlist", Toast.LENGTH_SHORT).show();
                            } else {


                                cart();
                             /*  Intent intent = new Intent(On_click_product.this, Wishlist.class);
                                intent.putExtra("product_id", product);
                                //finish();
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                                startActivity(intent);*/
                                //
                            }
                        }
                    });
                    imagev1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Picasso.with(On_click_product.this).load(imagepath)
                                    .placeholder(R.drawable.onload)
                                    .error(R.drawable.onload)
                                    .into(imagemain);

                        }
                    });

                    imagev2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Picasso.with(On_click_product.this).load(imagepath1)
                                    .placeholder(R.drawable.onload)
                                    .error(R.drawable.onload)
                                    .into(imagemain);

                        }
                    });

                    imagev3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Picasso.with(On_click_product.this).load(imagepath2)
                                    .placeholder(R.drawable.onload)
                                    .error(R.drawable.onload)
                                    .into(imagemain);

                        }
                    });

                    imagev4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Picasso.with(On_click_product.this).load(imagepath3)
                                    .placeholder(R.drawable.onload)
                                    .error(R.drawable.onload)
                                    .into(imagemain);

                        }
                    });
                    deal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+phone));
                            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(On_click_product.this, android.Manifest.permission.CALL_PHONE)) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            startActivity(callIntent);
                        }
                    });


                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                    e.printStackTrace();
                }

            }
        }

        LoginTask lt=new LoginTask();
        lt.execute();

    }

    private void cart() {
        class LoginTask extends AsyncTask<String,String,String>
        {       int v;
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onPostExecute(String s) {

                super.onPostExecute(s);
                try {
                    String jsonParser=gtjson;
                    JSONObject json=new JSONObject(jsonParser);
                    JSONArray result=json.getJSONArray("result");
                    for(int i=0;i<result.length();i++)
                    {   ItemObject home=new ItemObject();
                        JSONObject c1 = result.getJSONObject(i++);
                        home.product_id = c1.getInt("productid");


                        JSONObject c4 = result.getJSONObject(i++);
                        home.productName = c4.getString("name");

                        JSONObject c3= result.getJSONObject(i++);
                        home.productPrice = c3.getDouble("price");

                        JSONObject c2 = result.getJSONObject(i++);
                        home.productImage = c2.getString("path");

                        JSONObject c5 = result.getJSONObject(i++);
                        home.product = c5.getInt("views");
                        //Toast.makeText(Wishlist.this, "views="+home.product, Toast.LENGTH_SHORT).show();

                        home.state=1;
                        home.user_id=id;
                        ItemObject cart=new ItemObject(home.product_id,home.productImage,home.product,home.productName,home.productPrice,home.state,home.user_id);
                        ch.addShop(cart);
                        //v=home.productViews;
                        li.add(home);

                        Toast.makeText(On_click_product.this, "Item added to cart", Toast.LENGTH_SHORT).show();




                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }






            }
            @Override
            protected void onPreExecute() {


                super.onPreExecute();



            }
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected String doInBackground(String... strings) {
                HashMap<String, String> params = new HashMap<>();
                params.put("product_id", String.valueOf(getIntent().getIntExtra("product_id",100)));
                RequestHandler rh=new RequestHandler();
                gtjson= rh.sendPostRequest("http://192.168.43.181/olx/cart.php",params);
                //return gtjson;

                return gtjson;
            }



        }
        LoginTask lt=new LoginTask();
        lt.execute();
    }

}
