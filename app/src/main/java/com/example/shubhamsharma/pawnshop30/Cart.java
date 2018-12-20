package com.example.shubhamsharma.pawnshop30;

/**
 * Created by shubhamsharma on 02/03/17..
 */

public class Cart {
    public String name,image;
    public double price;
    public static int id=0;
    Cart(){

    }
    public Cart(String name, double price, String image) {
        this.name = name;
        this.price  = price;
        this.image = image;
        id++;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }
}
