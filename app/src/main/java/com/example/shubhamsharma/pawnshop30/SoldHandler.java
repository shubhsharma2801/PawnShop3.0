package com.example.shubhamsharma.pawnshop30;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by shubhamsharma on 07/05/17.
 */

public class SoldHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME="SoldList";


    public SoldHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE Sold (id integer PRIMARY KEY, product_name text, image text, user_id integer)";
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
    public void addShop(Sold cart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("product_name", cart.name); // Shop Name
        values.put("image", cart.path); // Shop Phone Number
        values.put("user_id", cart.id);



// Inserting Row
        db.insert("Sold", null, values);
        db.close(); // Closing database connection
    }

    // Getting All Shops
    public ArrayList<Sold> getAllShops(String user_id) {
        ArrayList<Sold> cartList = new ArrayList<Sold>();
// Select All Query
        String selectQuery ="SELECT * FROM Sold where user_id ='"+user_id+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Sold cart = new Sold();
                cart.name=cursor.getString(cursor.getColumnIndex("product_name"));
                cart.path=cursor.getString(cursor.getColumnIndex("image"));
                //cart.product_id= Integer.parseInt(cursor.getString(cursor.getColumnIndex("product_id")));
                cartList.add(cart);
            } while (cursor.moveToNext());
        }

// return contact list
        return cartList;
    }


// return contact list

    public void emptyCart(String product_id,String user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery ="Delete  FROM Sold  where user_id ='"+user_id+"'" ;
        db.execSQL(selectQuery);
        db.close();

    }

}
