package com.codewithhamad.headwaybuilders;

import android.content.Intent;
import android.os.Bundle;

import com.codewithhamad.headwaybuilders.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {


    private ImageView logo;
    private Animation fadeIn;

    private static int SPLASHSCREEN=1300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init views
        logo = (ImageView) findViewById(R.id.splashLogo);

        // setting anim to logo
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein_splash_activity);
        logo.startAnimation(fadeIn);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               Intent intent = new Intent(SplashActivity.this, MainActivity.class);
               startActivity(intent);
               finish();
            }
        },SPLASHSCREEN);

    }

}
