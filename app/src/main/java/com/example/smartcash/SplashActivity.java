package com.example.smartcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private final Timer timer = new Timer();
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> gotoMainActivity());
            }
        };
        timer.schedule(timerTask, 1500);
    }

    private void gotoMainActivity() {
        Intent intent = new  Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
