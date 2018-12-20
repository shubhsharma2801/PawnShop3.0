package com.example.shubhamsharma.pawnshop30;

/**
 * Created by yoga on 17-03-2017.
 */

public class ItemObject {
     int product_id;
     String productImage;

     int product;
     String productName;
     double productPrice;
    int state;
    int user_id;

    public ItemObject(int product_id, String productImage,  int product, String productName, double productPrice, int state, int user_id) {
        this.product_id = product_id;
        this.productImage = productImage;

        this.product = product;
        this.productName = productName;
        this.productPrice = productPrice;
        this.state = state;
        this.user_id = user_id;
    }

    public ItemObject(String productImage, String productName, int productPrice, int productViews, int product_id) {
        this.product_id = product_id;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.product = productViews;
    }

    public ItemObject() {

    }

    public int getProduct() {
        return product;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProduct_id() {
        return product_id;
    }
}