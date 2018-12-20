package com.example.shubhamsharma.pawnshop30;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by yoga on 20-03-2017.
 */

public class SplashScreen extends AppCompatActivity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Toast.makeText(this, getIntent().getStringExtra("postalcode"), Toast.LENGTH_SHORT).show();
        //final int pin=getIntent().getIntExtra("postalcode",100);
       // Toast.makeText(this, String.valueOf(pin), Toast.LENGTH_SHORT).show();
        setContentView(R.layout.splashscreen);
        AnimateNative();
        new Handler().postDelayed(new Runnable() {
            /*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */
            @Override
            public void run() {
                Intent intent;
                SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
                Boolean check= pref.getBoolean("Check",false);
                int user =pref.getInt("UserId",1000);
                String pin=getIntent().getStringExtra("postalcode");
                //Toast.makeText(SplashScreen.this, pin, Toast.LENGTH_SHORT).show();
                //Toast.makeText(SplashScreen.this, user, Toast.LENGTH_SHORT).show();
                if(check){
                    intent=new Intent(SplashScreen.this, HomePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("User",user);
                    intent.putExtra("PinCode",pin);

                }else {

                    intent=new Intent(SplashScreen.this,Interactive_login_register.class);
                    intent.putExtra("PinCode",pin);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                }
                startActivity(intent);
                finish();
            }
        },2000);

    }
    private void AnimateNative() {
        ImageView iv = (ImageView) findViewById(R.id.splashImage);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        iv.startAnimation(anim);
        anim.reset();
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {}
            @Override
            public void onAnimationRepeat(Animation arg0) {}
            @Override
            public void onAnimationEnd(Animation arg0) {}
        });

    }
}