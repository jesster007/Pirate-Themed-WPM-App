package com.example.wpmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class results extends AppCompatActivity {
    public static final String gType = "Game Type";
    public static final String diff = "Difficulty";
    String gameType;
    String difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        gameType = intent.getStringExtra(game.gType);
        difficulty = intent.getStringExtra(game.diff);
        String results = intent.getStringExtra(game.res);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
            if(gameType.equals("Race")){ //Sets different message for each gamemode
                TextView displayMessage = findViewById(R.id.gameText);
                TextView resultMessage = findViewById(R.id.resultOutput);
                if(results.equals("1")){ //if they succeeded
                    displayMessage.setText(R.string.success);
                    results = difficulty + " difficulty";
                    resultMessage.setText(results);
                }
                else if(results.equals("0")){ //if they failed
                    displayMessage.setText(R.string.failure);
                    results = difficulty+ " difficulty";
                    resultMessage.setText(results);
                }
            }
            else if(gameType.equals("Quote")){ //Sets different message for each gamemode
                TextView displayMessage = findViewById(R.id.gameText);
                displayMessage.setText(R.string.yarr_you_finished_the_message_in);
                TextView resultMessage = findViewById(R.id.resultOutput);
                results = results + " seconds";
                resultMessage.setText(results);
            }
            else if(gameType.equals("Timer")) { //Sets different message for each gamemode
                TextView displayMessage = findViewById(R.id.gameText);
                displayMessage.setText(R.string.yarr_you_typed_at);
                TextView resultMessage = findViewById(R.id.resultOutput);
                results = results + " WPM";
                resultMessage.setText(results);
            }
        }
    public void main_menu (View view) { //go back to the options screen
        Intent intent = new Intent (this, options.class);
        startActivity(intent);
    }
    public void retry (View view) {
        Intent intent = new Intent (this, game.class); //go back to the game type you just tried
        intent.putExtra(gType, gameType);
        intent.putExtra(diff, difficulty);
        startActivity(intent);
    }
}