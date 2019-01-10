package com.srinivas.peopletracke;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences.Editor ss = getSharedPreferences("status",MODE_PRIVATE).edit();
                ss.putString("status","start");
                ss.commit();
                Intent main = new Intent(Splash.this,MainActivity.class);
                startActivity(main);
            }
        },2000);
    }
}
