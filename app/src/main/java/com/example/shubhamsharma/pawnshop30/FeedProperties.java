package com.example.shubhamsharma.pawnshop30;

/**
 * Created by zero on 3/11/15.
 */
public class FeedProperties {
    private String price;
    private String views;
    private String title;
    private int thumbnail;


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

    public String getViews() {
        return views;
    }

    public void setViews(String views)

    {
        this.views = views;
    }
}
