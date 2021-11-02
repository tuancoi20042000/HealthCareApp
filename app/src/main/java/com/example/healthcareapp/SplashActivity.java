package com.example.healthcareapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class SplashActivity extends AppCompatActivity {
    Handler h = new Handler();
    ProgressBar p;
    boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        p = findViewById(R.id.progressBar);
        Sprite animation = new ThreeBounce();
        p.setIndeterminateDrawable(animation);
        isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(SplashActivity.this.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            isConnected = true;
        }
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (isConnected) {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, ErrorActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}