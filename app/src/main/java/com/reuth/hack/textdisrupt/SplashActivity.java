package com.reuth.hack.textdisrupt;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class SplashActivity extends AppCompatActivity {

    private static boolean splashLoaded = false;

    private static final int DELAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);



        if (!splashLoaded) {
            setContentView(R.layout.activity_splash);

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(getBaseContext(), ImportTextActivity.class));
                    finish();
                }
            }, DELAY_TIME);

            splashLoaded = true;
        }
        else {
            Intent importTextActivity = new Intent(getBaseContext(), ImportTextActivity.class);
            importTextActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(importTextActivity);
            finish();
        }

    }
}
