package com.example.shubhamsharma.pawnshop30;

/**
 * Created by yoga on 17-03-2017.
 */


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.shubhamsharma.pawnshop30.RequestHandler.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class UserAdsViewFragment extends Fragment {
    private CardView cardView;
    private RecyclerView recyclerView;
    private CardViewAdapter_user mAdapter;
    String gtjson;

    private ArrayList<FeedProperties_user> os_versions;
    public UserAdsViewFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_ads_view, container, false);
        cardView = (CardView)view.findViewById(R.id.card3);
        recyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
        new Task(view).execute();

        os_versions = new ArrayList<FeedProperties_user>();


        return view;
    }


        class Task extends AsyncTask<String,String,String> {
            View view;
            int product_id;
            public Task(View v) {
                this.view=v;

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,1,false));
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
                mAdapter = new CardViewAdapter_user(os_versions,getContext());
                recyclerView.setAdapter(mAdapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
                } else {
                    linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
                }
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

            }


            @Override
            protected String doInBackground(String... strings) {
                HashMap<String, String> params = new HashMap<>();
                SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
                int id = pref.getInt("UserId", 1000);
//            Toast.makeText(HomePageActivity.this, String.valueOf(getIntent().getIntExtra("PinCode",100)), Toast.LENGTH_SHORT).show();
                params.put("userid", String.valueOf(id));
                RequestHandler rh=new RequestHandler();
                gtjson= rh.sendPostRequest("http://192.168.43.181/olx/MyAds.php",params);
                //return gtjson;
                try {
                    String jsonParser=gtjson;
                    final int[] icons_sell = {R.drawable.mark_box};
                    JSONObject json=new JSONObject(jsonParser);
                    JSONArray result=json.getJSONArray("result");
                    for(int i=0;i<result.length();i++)
                    {
                        JSONObject c4 = result.getJSONObject(i++);
                        String productName = c4.getString("name");
                        JSONObject c2 = result.getJSONObject(i++);
                        int productPrice = c2.getInt("price");
                        JSONObject c5= result.getJSONObject(i++);
                        String productImage = c5.getString("path1");
                        JSONObject c6= result.getJSONObject(i);
                        int productid = c6.getInt("product_id");
                        FeedProperties_user home= new FeedProperties_user();
                        home.setTitle(productName);
                        home.setPrice(String.valueOf(productPrice));
                        home.setThumbnail(productImage);
                        home.setsell(icons_sell[0]);
                        home.setId(productid);
                        home.setContext(getContext());
                        os_versions.add(home);
                        //data.add(home);





                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return gtjson;
            }
        }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}