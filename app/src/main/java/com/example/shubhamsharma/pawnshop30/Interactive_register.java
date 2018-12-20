package com.example.shubhamsharma.pawnshop30;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yoga on 26-03-2017.
 */

public class Interactive_register  extends Fragment{
    EditText name,add,email,mobile,password;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register_card, container, false);
        CardView cardView = (CardView) view.findViewById(R.id.card2);
        Button mybutton2=(Button) view.findViewById(R.id.go_register);

        name=(EditText)view.findViewById(R.id.user_name);
        add=(EditText)view.findViewById(R.id.user_address);
        email=(EditText)view.findViewById(R.id.user_id);
        mobile=(EditText)view.findViewById(R.id.user_mobile_number);
        password=(EditText)view.findViewById(R.id.create_password);
        mybutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Registered Sucessfully", Toast.LENGTH_SHORT).show();
                upload();
                Toast.makeText(getActivity(), "Now Login", Toast.LENGTH_LONG).show();
                Intent intent=getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().finish();
                startActivity(intent);

            }
        });
        return view;
    }

    private void upload() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.181/OLX/register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        //loading.dismiss();
                        //Showing toast message of the response
                        //Toast.makeText(getContext(), s , Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //loading.dismiss();
                        //Showing toast
                        Toast.makeText(getContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences pre = getContext().getSharedPreferences("pre", MODE_PRIVATE);
           int  id=pre.getInt("PostalCode",100);

                //String p_life=
                //String p_weight=weight.getText().toString().trim();
                //Creating parameters
                String pname=name.getText().toString();
                String padd=add.getText().toString();
                String pemail=email.getText().toString();
                String pmobile=mobile.getText().toString();
                String ppass=password.getText().toString();
                Map<String,String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("name",pname);
                params.put("email",pemail);
                params.put("password",ppass);
                params.put("address",padd);
                params.put("phone",pmobile);
                params.put("pincode", String.valueOf(id));
                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

}
