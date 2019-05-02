package com.mamour.myquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import com.mamour.myquiz.Model.Client;
import io.realm.Realm;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // handler post delayed
        SharedPreferences preferences = getSharedPreferences("loginPrefs",Context.MODE_PRIVATE);
        if (preferences.getString("username","").equals("")){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //demarrer une page
                    Intent d = new Intent(SplashScreen.this, loginActivity.class);
                    startActivity(d);
                    finish();
            }
        }, 3000);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //demarrer une page
                    Intent d = new Intent(SplashScreen.this, ViewDatabase.class);
                    startActivity(d);
                    finish();
                }
            }, 3000);

        }
    }
}
