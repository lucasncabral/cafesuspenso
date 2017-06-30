package com.cafesuspenso.ufcg.cafesuspenso.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cafesuspenso.ufcg.cafesuspenso.R;


public class SplashActivity extends Activity {
    private static final long SPLASH_TIME_OUT = 4978;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView myImageView= (ImageView)findViewById(R.id.imgLogo);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        myImageView.startAnimation(myFadeInAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openLoginScreen();
            }
        }, SPLASH_TIME_OUT);

    }



    private void openLoginScreen() {

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean isLogged = sharedPref.getBoolean("isLogged", false);

        Intent i;
        if(!isLogged){
            i = new Intent(SplashActivity.this, LoginActivity.class);
        } else {
            i = new Intent(SplashActivity.this, MainActivity.class);
        }

        startActivity(i);
        finish();
    }
}
