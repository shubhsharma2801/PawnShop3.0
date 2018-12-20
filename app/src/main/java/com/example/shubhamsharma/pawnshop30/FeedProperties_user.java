package com.example.shubhamsharma.pawnshop30;

import android.content.Context;

/**
 * Created by zero on 3/11/15.
 */
public class FeedProperties_user {
    private String price;
    private String title;
    private String thumbnail;
    private int sell;
    public int id;
    private String description;
    Context context;


    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title)

    {
        this.title = title;
    }


    public String getPrice() {
        return price;
    }


    public void setPrice(String price)

    {
        this.price = price;
    }

    public String getThumbnail() {return thumbnail;}

    public void setThumbnail(String thumbnail) {this.thumbnail = thumbnail;}

    public int getSell() {return sell;}

    public void setsell(int like_thumbnail) {this.sell = like_thumbnail;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
