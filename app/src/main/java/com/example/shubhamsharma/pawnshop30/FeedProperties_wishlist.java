package com.example.shubhamsharma.pawnshop30;

/**
 * Created by zero on 3/11/15.
 */
public class FeedProperties_wishlist {
    private String price;
    private String title;
    private int thumbnail;
    private String views;
    private int delete_icon;

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public int getDelete_icon() {
        return delete_icon;
    }

    public void setDelete_icon(int delete_icon) {
        this.delete_icon = delete_icon;
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

    public int getThumbnail() {return thumbnail;}

    public void setThumbnail(int thumbnail) {this.thumbnail = thumbnail;}
}
