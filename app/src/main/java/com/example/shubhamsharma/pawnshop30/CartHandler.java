package com.example.shubhamsharma.pawnshop30;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CartHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME="ProductList";


    public CartHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE Product (id integer PRIMARY KEY, product_name text, product_price integer, product_image text, state int, views int, user_id int, product_id int)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS Product");
// Creating tables again
        onCreate(db);
    }
    // Adding new shop
    public void addShop(ItemObject cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("product_name", cart.productName); // Shop Name
        values.put("product_price", cart.productPrice); // Shop Phone Number
        values.put("product_image", cart.productImage);
        values.put("views",cart.product);
        values.put("state",cart.state);
        values.put("user_id",cart.user_id);
        values.put("product_id",cart.product_id);


// Inserting Row
        db.insert("Product", null, values);
        db.close(); // Closing database connection
    }

    // Getting All Shops
    public ArrayList<ItemObject> getAllShops(String user_id) {
        ArrayList<ItemObject> cartList = new ArrayList<ItemObject>();
// Select All Query
        String selectQuery ="SELECT * FROM Product where user_id ='"+user_id+"'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemObject cart = new ItemObject();
                cart.productName=cursor.getString(cursor.getColumnIndex("product_name"));
                cart.productPrice= Double.parseDouble(cursor.getString(cursor.getColumnIndex("product_price")));
                cart.productImage=cursor.getString(cursor.getColumnIndex("product_image"));
                cart.product = Integer.parseInt(cursor.getString(cursor.getColumnIndex("views")));
                cart.state= Integer.parseInt(cursor.getString(cursor.getColumnIndex("state")));
                cart.product_id= Integer.parseInt(cursor.getString(cursor.getColumnIndex("product_id")));
                cartList.add(cart);
            } while (cursor.moveToNext());
        }

// return contact list
        return cartList;
    }
    public int getState(String product_id,String user_id) {
        int state = 0;
// Select All Query
        String selectQuery ="SELECT state FROM Product where product_id ='"+product_id+"' and user_id ='"+user_id+"'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                state= Integer.parseInt(cursor.getString(0));
                /*cart.setId(Integer.parseInt(cursor.getString(0)));
                cart.setName(cursor.getString(1));
                cart.setPrice(cursor.getInt(2));
                cart.image=cursor.getString(3);*/
// Adding contact to list

            } while (cursor.moveToNext());
        }

// return contact list
        return state;
    }
    public void emptyCart(String product_id,String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery ="Delete  FROM Product where product_id ='"+product_id+"' and user_id ='"+user_id+"'" ;
        db.execSQL(selectQuery);
        db.close();

    }


}