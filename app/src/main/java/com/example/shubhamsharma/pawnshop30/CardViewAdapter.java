package com.example.shubhamsharma.pawnshop30;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by zero on 3/11/15.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
    Context context;
    private static ArrayList<ItemObject> dataSet;

    public CardViewAdapter(ArrayList<ItemObject> os_versions, Context context) {
        this.context=context;
        dataSet = os_versions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

// create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.card_view, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        ItemObject fp = dataSet.get(i);
        viewHolder.view.setText(fp.getProductName());
        viewHolder.view2.setText(String.valueOf(fp.getProduct())+"views");
        viewHolder.view3.setText("Rs:"+String.valueOf(fp.getProductPrice()));
        //viewHolder.iconView.setImageResource(fp.getProductImage());
        Picasso.with(context).load(fp.getProductImage())
                .placeholder(R.drawable.onload)
                .error(R.drawable.onload)
                .into(viewHolder.iconView);
        viewHolder.product_id=fp.getProduct_id();
        // viewHolder.iconView.setBackgroundResource(fp.getThumbnail());

        viewHolder.feed = fp;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView view;
        public TextView view2;
        public TextView view3;
               int product_id;
        public ImageView iconView;
        public ItemObject feed;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);

            view = (TextView) itemLayoutView
                    .findViewById(R.id.product_name);
            view2 = (TextView) itemLayoutView
                    .findViewById(R.id.views);
            view3 = (TextView) itemLayoutView
                    .findViewById(R.id.product_price);
            iconView = (ImageView) itemLayoutView
                    .findViewById(R.id.product_image);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),On_click_product.class);
                    intent.putExtra("product_id",feed.product_id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}