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
public class SoldAdapter extends RecyclerView.Adapter<SoldAdapter.ViewHolder> {
    Context context;
    private static ArrayList<Sold> dataSet;

    public SoldAdapter(ArrayList<Sold> os_versions, Context context) {
        this.context=context;
        dataSet = os_versions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
// create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.card_view_2, null);
        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        Sold fp = dataSet.get(i);
        viewHolder.view.setText(fp.getName());
      Picasso.with(context).load(fp.getPath())
                .placeholder(R.drawable.onload)
                .error(R.drawable.onload)
                .into(viewHolder.iconView);
       // viewHolder.id=fp.getId();
      //  viewHolder.context=fp.getContext();


        viewHolder.feed = fp;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }




    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView view;
        public ImageView iconView;
        public Sold feed;
        public int id;
        public Context context;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);


            view = (TextView) itemLayoutView
                    .findViewById(R.id.product_name);
            iconView = (ImageView) itemLayoutView
                    .findViewById(R.id.product_image);
        }
    }

}