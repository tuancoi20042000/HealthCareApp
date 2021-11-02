package com.example.healthcareapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ErrorActivity extends AppCompatActivity {
    private TextView btTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        btTryAgain = findViewById(R.id.btTryAgain);
        btTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(ErrorActivity.this.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    Intent intent = new Intent(ErrorActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ErrorActivity.this, "Vui lòng kiểm tra lại mạng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}