package com.example.shubhamsharma.pawnshop30;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by zero on 3/11/15.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class CardViewAdapter_user extends RecyclerView.Adapter<CardViewAdapter_user.ViewHolder> {
    Context context;
    private static ArrayList<FeedProperties_user> dataSet;
    CartHandler ch;

    public CardViewAdapter_user(ArrayList<FeedProperties_user> os_versions, Context context) {
        this.context=context;
        dataSet = os_versions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
// create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.mysell_card, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        FeedProperties_user fp = dataSet.get(i);
        viewHolder.view.setText(fp.getTitle());
        viewHolder.view3.setText(String.valueOf(fp.getPrice()));
        viewHolder.product_sold.setImageResource(fp.getSell());
        Picasso.with(context).load(fp.getThumbnail())
                .placeholder(R.drawable.onload)
                .error(R.drawable.onload)
                .into(viewHolder.iconView);
        viewHolder.id=fp.getId();
        viewHolder.context=fp.getContext();


        viewHolder.feed = fp;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private int flag=0;
        public TextView view;
        public TextView view3;
        public TextView view4;
        public ImageView iconView;
        public ImageView product_sold;
        public FeedProperties_user feed;
        public int id;
        public Context context;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);


            view = (TextView) itemLayoutView
                    .findViewById(R.id.product_name);

            view3 = (TextView) itemLayoutView
                    .findViewById(R.id.product_price);
            iconView = (ImageView) itemLayoutView
                    .findViewById(R.id.product_image);

            product_sold=(ImageView) itemLayoutView.findViewById(R.id.mark_sold);
            product_sold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final SoldHandler sh=new SoldHandler(feed.context);
                    SharedPreferences pref = feed.context.getSharedPreferences("pref", MODE_PRIVATE);
                    int id = pref.getInt("UserId", 1000);
                    Sold s=new Sold();
                    s.name=feed.getTitle();
                    s.path=feed.getThumbnail();
                    s.id=id;
                    sh.addShop(s);
                    if(flag==0) {
                        product_sold.setImageResource(R.drawable.ticked);
                        Toast.makeText(product_sold.getContext(), "The product will be removed in few minutes", Toast.LENGTH_LONG).show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.181/OLX/RemoveProduct.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        //Dismissing the progress dialog
                                        //loading.dismiss();
                                        //Showing toast
                                       // Toast.makeText(getContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                SharedPreferences pref = feed.context.getSharedPreferences("pref", MODE_PRIVATE);
                                int id = pref.getInt("UserId", 1000);

                                Map<String,String> params = new Hashtable<String, String>();
                                //Adding parameters
                                params.put("userid", String.valueOf(id));
                                params.put("productid", String.valueOf(feed.id));
                                return params;
                            }
                        };
                        //Creating a Request Queue
                        RequestQueue requestQueue = Volley.newRequestQueue(feed.context);
                        //Adding request to the queue
                        requestQueue.add(stringRequest);
                        flag=1;
                    }
                    else
                    {
                        product_sold.setImageResource(R.drawable.mark_box);
                        flag=0;

                    }
                }
            });
        }

    }

}