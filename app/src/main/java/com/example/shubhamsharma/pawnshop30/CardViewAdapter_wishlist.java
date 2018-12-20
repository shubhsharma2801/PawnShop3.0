package com.example.shubhamsharma.pawnshop30;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by zero on 3/11/15.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class CardViewAdapter_wishlist extends RecyclerView.Adapter<CardViewAdapter_wishlist.ViewHolder> {
    static CartHandler ch ;
    private static ArrayList<ItemObject> dataSet;
    Context context;
    static int id;

    public CardViewAdapter_wishlist(ArrayList<ItemObject> os_versions, Context context) {
        this.context=context;
        dataSet = os_versions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
// create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.mywishlist_card, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ItemObject fp = dataSet.get(i);
        viewHolder.view.setText(fp.getProductName());
        //viewHolder.view2.setText(String.valueOf(fp.product)+"views");
        viewHolder.view3.setText("Rs:"+String.valueOf(fp.getProductPrice()));
        Picasso.with(context).load(fp.getProductImage())
                .placeholder(R.drawable.onload)
                .error(R.drawable.onload)
                .into(viewHolder.iconView);
        viewHolder.product_id=fp.getProduct_id();
        viewHolder.delete_icon.setImageResource(R.drawable.delete_product);
        viewHolder.feed = fp;
        ch=new CartHandler(context);
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        id=pref.getInt("UserId",1000);
        viewHolder.state=context;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView view;
        public TextView view3;
        public TextView view2;
        public ImageView iconView;
        public ImageView delete_icon;
        public ItemObject feed;
        int product_id;
        Context state;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);


            view = (TextView) itemLayoutView
                    .findViewById(R.id.product_name);
           // view2 =(TextView)itemLayoutView
             //       .findViewById(R.id.product_views);
            view3 = (TextView) itemLayoutView
                    .findViewById(R.id.product_price);
            iconView = (ImageView) itemLayoutView
                    .findViewById(R.id.product_image);
            delete_icon = (ImageView) itemLayoutView
                    .findViewById(R.id.product_delete);
            delete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ch.emptyCart(String.valueOf(feed.product_id),String.valueOf(id));
                    Intent intent=new Intent(view.getContext(),Wishlist.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    ((Activity) state).finish();
                    view.getContext().startActivity(intent);
                    ((Activity) state).overridePendingTransition(0,0);
                }
            });

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(),On_click_product.class);
                    intent.putExtra("product_id",feed.product_id);
                    v.getContext().startActivity(intent);

                }
            });
        }
        }
    }

