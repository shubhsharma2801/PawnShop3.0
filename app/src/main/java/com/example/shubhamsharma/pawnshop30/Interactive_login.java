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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yoga on 26-03-2017.
 */

public class Interactive_login extends Fragment {
    EditText name,password;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String pname,ppass;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_card, container, false);
        CardView cardView = (CardView) view.findViewById(R.id.card);
        Button mybutton1=(Button) view.findViewById(R.id.go_login);
        pref= getContext().getSharedPreferences("pref", MODE_PRIVATE);

        editor = pref.edit();
        name=(EditText)view.findViewById(R.id.user);
        password=(EditText)view.findViewById(R.id.password);

        //Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"Billabong.ttf");
      //  mybutton1.setTypeface(face);
        mybutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(getActivity(),HomePageActivity.class);
                //startActivity(intent);
                upload();
            }
        });
        return view;
    }
    private void upload() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.181/OLX/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String gtjson) {
                        String jsonParser=gtjson;
                        JSONObject json= null;
                        //Toast.makeText(getContext(),gtjson, Toast.LENGTH_SHORT).show();
                        try {
                            json = new JSONObject(jsonParser);
                            JSONArray result=json.getJSONArray("result");
                            JSONObject c=result.getJSONObject(0);
                            int message=c.getInt("message");

                            if (message==1) {
                                JSONObject c1=result.getJSONObject(1);
                                int user_id=c1.getInt("user_id");
                                //Toast.makeText(getContext(), String.valueOf(user_id), Toast.LENGTH_SHORT).show();
                                editor.putInt("UserId",user_id);
                                openProfile(user_id);
                            }
                            else {
                                Toast.makeText(getContext(), "invalid username or password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //loading.dismiss();
                        //Showing toast
//                        Toast.makeText(getContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                //String p_life=
                //String p_weight=weight.getText().toString().trim();
                //Creating parameters
                pname=name.getText().toString();
                ppass=password.getText().toString();
                Map<String,String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("user1",pname);
                params.put("pass1",ppass);


                //returning parameters
                return params;
            }
        };
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }
    private void openProfile(int id) {
        editor.putBoolean("Check",true);
        editor.putString("UserName",pname);
        editor.putString("Password",ppass);
        editor.commit();
        Intent intent=new Intent(getView().getContext(),HomePageActivity.class);
        Toast.makeText(getContext(), pname, Toast.LENGTH_SHORT).show();
        intent.putExtra("user_id",id);

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(intent);
        getActivity().finish();
    }
}
