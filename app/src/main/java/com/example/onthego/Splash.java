package com.example.onthego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_splash );
        logo = findViewById ( R.id.logo );
        logo.animate ().translationX ( 1600 ).setDuration ( 1000 ).setStartDelay (2500 );
        new Handler ().postDelayed ( new Runnable () {
            @Override
            public void run() {
                Intent intent = new Intent (Splash.this,MainActivity.class);
                startActivity ( intent );
                finish ();
            }
        },3000 );

    }
}