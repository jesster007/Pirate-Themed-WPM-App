package com.example.wpmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

public class options extends AppCompatActivity {
    public static final String gType = "Game Type";
    String gameType;
    String difficulty;
    public static final String diff = "Difficulty";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }
    public void infoPopup(View view){ //toggles the visibility of the info box
        ImageButton b = findViewById(R.id.infoButton);
        ImageView img = findViewById(R.id.infoScreen);
        if (b.getVisibility() == img.getVisibility()) {
            img.setVisibility(View.INVISIBLE);
        } else {
            img.setVisibility(View.VISIBLE);
        }
    }
    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void Startgame(View view) { //get gametype and difficulty and call the game activity
        Intent intent = new Intent(this, game.class);
        Spinner spinnerGameType = (Spinner) findViewById(R.id.spinner);
        String gameType = spinnerGameType.getSelectedItem().toString();
        Spinner spinnerDifficulty = (Spinner) findViewById(R.id.spinner2);
        String difficulty = spinnerDifficulty.getSelectedItem().toString();
        intent.putExtra(gType, gameType);
        intent.putExtra(diff, difficulty);
        startActivity(intent);
    }
}