package com.example.shubhamsharma.pawnshop30;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.example.shubhamsharma.pawnshop30.ViewPhoto.getImageOrientation;

/**
 * Created by yoga on 07-04-2017.
 */

public class Product_details extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int flag = 0,check1=0,check2=0;
    File[] listFile;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Button save;
    Button next;
    String pincode;
    TextView txt;
    ArrayList<String> f = new ArrayList<String>();// list of file paths
    String type,title,name,brand,price,description,userid,catagory;
    String image[]=new String[4];
    TextInputEditText layout1,layout2,layout3;




    private GoogleApiClient client;
private TextWatcher textWatcher=new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    checkFieldsForEmptyValues();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_upload_page);
        final TextView txt=(TextView) findViewById(R.id.project_type);
    layout1 = (TextInputEditText) findViewById(R.id.name_upload);
    layout2 = (TextInputEditText) findViewById(R.id.price_upload);
    layout3 = (TextInputEditText) findViewById(R.id.product_description_upload);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);

        userid = String.valueOf(pref.getInt("UserId",1000));
        SharedPreferences pre = getBaseContext().getSharedPreferences("pre", MODE_PRIVATE);
        pincode=String.valueOf(pre.getInt("PostalCode",100));

        //radio button
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Category");
        categories.add("Computer Science");
        categories.add("Electronics");
        categories.add("Mechanical");
        categories.add("Others");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        //spinner ends
        layout1.addTextChangedListener(textWatcher);
        layout2.addTextChangedListener(textWatcher);
        layout3.addTextChangedListener(textWatcher);
        checkFieldsForEmptyValues();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton:
                        type="Project";
                        break;
                    case R.id.radioButton2:
                        type="Component";
                        break;
                }
            }
        });
        f=getIntent().getStringArrayListExtra("image");

        image=getImageString();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Product_details.this, "save", Toast.LENGTH_SHORT).show();
                upload();
            }
        });

    }
  private void checkFieldsForEmptyValues()
  {
      String s1,s2,s3;
      save = (Button) findViewById(R.id.save_button);
      save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
      s1=layout1.getText().toString();
      s2=layout2.getText().toString();
      s3=layout3.getText().toString();

      if(s1.equals("") && s2.equals("") && s3.equals(""))
      {
          save.setEnabled(false);
      }
      else if( !s1.equals("") && s2.equals("") && s3.equals(""))
      {
          save.setEnabled(false);
      }
      else if( !s1.equals("") && !s2.equals("") && s3.equals(""))
      {
          save.setEnabled(false);
      }
      else if( !s1.equals("") && !s2.equals("") && !s3.equals(""))
      {
          save.setEnabled(true);
      }
      else
          save.setEnabled(false);
  }
    private String[] getImageString() {
        for (int j=0;j<4; j++)
        {   Matrix matrix = new Matrix();
            matrix.postRotate(getImageOrientation(this.f.get(j)));
            //f.add(listFile[i--].getAbsolutePath());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap myBitmap = BitmapFactory.decodeFile(this.f.get(j),options);
            myBitmap= Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),myBitmap.getHeight(), matrix, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] imageBytes = baos.toByteArray();
            image[j] = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        }
        return image;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        if (position > 0) {
            String item = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item
            switch (item){
                case "Computer Science":
                    catagory= String.valueOf(0);
                    break;
                case "Electronics":
                    catagory= String.valueOf(1);
                    break;
                case "Mechanical":
                    catagory= String.valueOf(2);
                    break;
                case "Others":
                    catagory= String.valueOf(3);
                    break;
                default:
                    catagory=String.valueOf(0);
                    break;


            }
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
    }
     private void upload() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.181/OLX/product_upload.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String gtjson) {

                        Toast.makeText(Product_details.this, "Add Posted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Product_details.this,HomePageActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(getBaseContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                name=layout1.getText().toString();
                price=layout2.getText().toString();
                description=layout3.getText().toString();

                String path1=image[0];
                String path2=image[1];
                String path3=image[2];
                String path4=image[3];
                Map<String,String> params = new Hashtable<String, String>();
                params.put("path1",path1);
                params.put("path2",path2);
                params.put("path3",path3);
                params.put("path4",path4);
                params.put("type",type);
                params.put("name",name);
                params.put("category",catagory);
                params.put("price",price);
                params.put("description",description);
                params.put("userid",userid);
                params.put("pincode", String.valueOf(pincode));




                return params;
            }
        };
        //int x=2;
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());

        requestQueue.add(stringRequest);
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Product_details Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

