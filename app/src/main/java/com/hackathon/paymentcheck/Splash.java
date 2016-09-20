package com.hackathon.paymentcheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try
                {
                    Thread.sleep(2000);
                    Intent i = new Intent(Splash.this, Complain.class);
                    startActivity(i);

                }
                catch (Exception e)
                {

                }


            }
        });
        thread.start();

    }

}
