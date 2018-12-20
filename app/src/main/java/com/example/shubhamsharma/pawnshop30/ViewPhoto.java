package com.example.shubhamsharma.pawnshop30;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by yoga on 21-04-2017.
 */
public class ViewPhoto extends Activity
{
    private ImageAdapter imageAdapter;
    ArrayList<String> f = new ArrayList<String>();// list of file paths
    File[] listFile;
    private TextView textView1,textView2;
    private ImageView imgPreview;
    Button upload,yes,close;
    WebView progress;
    String image[]=new String[4];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        close=(Button)findViewById(R.id.close_btn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ViewPhoto.this,HomePageActivity.class);
                startActivity(i);
            }
        });
        textView1=(TextView) findViewById(R.id.textview1);
        textView2=(TextView) findViewById(R.id.textview2);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        progress=(WebView) findViewById(R.id.progress);
        upload=(Button)findViewById(R.id.upload_product);
        upload.setVisibility(View.VISIBLE);
        yes=(Button)findViewById(R.id.btnCapturePicture);
        yes.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        CardView show_images=(CardView) findViewById(R.id.card_view);
        show_images.setVisibility(View.VISIBLE);
        getFromSdcard();
        new Taskk().execute();
        GridView imagegrid = (GridView) findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);

    }





    public void getFromSdcard()
    {
        File file= new File(android.os.Environment.getExternalStorageDirectory(),"Pictures/Hello Camera");

        if (file.isDirectory())
        {
            listFile = file.listFiles();
            for (int i = listFile.length-1,j=0;j<4; j++)
            {
                f.add(listFile[i--].getAbsolutePath());
            }
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public ImageAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return f.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(
                        R.layout.galleryitem,null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Matrix matrix = new Matrix();
            matrix.postRotate(getImageOrientation(f.get(position)));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position),options);
            myBitmap= Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(),myBitmap.getHeight(), matrix, true);
            holder.imageview.setImageBitmap(myBitmap);
            return convertView;
        }

    }
    public static int getImageOrientation(String imagePath){
        int rotate = 0;
        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }


    class ViewHolder {
        ImageView imageview;
    }
    private class Taskk extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(ViewPhoto.this,Product_details.class);
                    i.putStringArrayListExtra("image",f);
                    startActivity(i);

                }
            });

        }
        @Override
        protected String doInBackground(String... strings) {

            return null;
        }
    }
}

