package com.example.wpmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String gType = "Game Type";
    public static final String diff = "Difficulty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void game_settings(View view) {
     Intent intent = new Intent(this, GameSettings.class);
     startActivity(intent);
    }
    public void options(View view) {
        Intent intent = new Intent(this, options.class);
        startActivity(intent);
    }

}