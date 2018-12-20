package com.example.shubhamsharma.pawnshop30;

/**
 * Created by yoga on 17-03-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends BaseAdapter{

    private LayoutInflater layoutinflater;
    private List<ItemObject> listStorage;
    private Context context;

    public CustomAdapter(Context context, List<ItemObject> customizedListView) {
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.card_view, parent, false);
            listViewHolder.cardView =(CardView)convertView.findViewById(R.id.card_view);
            listViewHolder.productImage = (ImageView)convertView.findViewById(R.id.product_image);
            listViewHolder.productName  = (TextView)convertView.findViewById(R.id.product_name);
            listViewHolder.productPrice = (TextView)convertView.findViewById(R.id.product_price);
            listViewHolder.productViews = (TextView)convertView.findViewById(R.id.views);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(context).load(listStorage.get(position).getProductImage())
                .placeholder(R.drawable.onload)
                .error(R.drawable.onload)
                .into(listViewHolder.productImage);
        //listViewHolder.productImage.setImageResource(listStorage.get(position).getProductImage());
        listViewHolder.productName.setText(listStorage.get(position).getProductName());
        listViewHolder.productPrice.setText("Rs:"+String.valueOf(listStorage.get(position).getProductPrice()));
        listViewHolder.productViews.setText(String.valueOf(listStorage.get(position).getProduct())+"views");
        listViewHolder.product_id=listStorage.get(position).getProduct_id();
        listViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),On_click_product.class);
                intent.putExtra("product_id",listViewHolder.product_id);
                view.getContext().startActivity(intent);
            }
        });
        return convertView;

    }

    static class ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView productViews;
        int product_id;
        CardView cardView;
    }
}