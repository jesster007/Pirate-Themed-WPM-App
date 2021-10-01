package com.example.wpmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class GameSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
    }
    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
    }
    //func to start music service
    public void startmusic (View view) {
        Intent svc = new Intent(this, BackgroundSoundService.class);
        startService(svc);
    }
    //funct to stop the music service
    public void stopMusic (View view) {
        Intent svc = new Intent(this, BackgroundSoundService.class);
        stopService(svc);
    }
}