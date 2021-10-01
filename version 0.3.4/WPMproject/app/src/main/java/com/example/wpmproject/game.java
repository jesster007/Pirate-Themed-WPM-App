package com.example.wpmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class game extends AppCompatActivity {
    public static final String gType = "Game Type"; //The key for the gametype intent
    public static final String diff = "Difficulty"; //The key for the difficulty intent
    public static final String res = "Results"; //the key for the results intent
    public CountDownTimer myCountDown;
    String gameType;
    String difficulty;
    public String[] userInput = new String[45]; //allocate 45 max slots for each array
    public String[] correctInput = new String[45];
    public int counter = 40; //Timer at the top of the screen's start point
    public int correctNeeded = 1; //Value given later after arrays generated
    public String results;
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        gameType = intent.getStringExtra(options.gType); //Get the gametype and difficulty from previous intent
        difficulty = intent.getStringExtra(options.diff);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        displayText(gameType, difficulty); //calls the function to generate the needed text and display it on the screen
        final TextView countDown = findViewById(R.id.textView);
        if (gameType.equals("Timer") || gameType.equals("Race")) { //Counter counts down for Timer and Race
            myCountDown = new CountDownTimer(41000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    runGame(gameType, difficulty); //get the user input, determines if the game needs to end
                    countDown.setText(String.valueOf(counter));
                    counter--;
                }

                @Override
                public void onFinish() {
                    assignResults(gameType, difficulty); //calculate results
                    nextScreen(gameType, difficulty); //pass everything to the results screen
                }
            }.start();
        } else if (gameType.equals("Quote")) { //Counter counts up for Quote
            counter = 0;
                myCountDown = new CountDownTimer(1000000, 1000) { //timer is theoretically infinite, should be more than enough time for the user to finish
                    @Override
                    public void onTick(long millisUntilFinished) {
                        runGame(gameType, difficulty);
                        countDown.setText(String.valueOf(counter));
                        counter++;
                    }

                    @Override
                    public void onFinish() {
                        assignResults(gameType, difficulty);
                        nextScreen(gameType, difficulty);
                    }
                }.start();
        }
    }
    public void retry(View view){ //Reset the game to the basic layout and reset the variables
        counter = 40;
        myCountDown.cancel(); //cancel previos countdown timer
        setContentView(R.layout.activity_game);
        displayText(gameType, difficulty);
        final TextView countDown = findViewById(R.id.textView);
        if (gameType.equals("Timer") || gameType.equals("Race")) {
            myCountDown = new CountDownTimer(41000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    runGame(gameType, difficulty);
                    countDown.setText(String.valueOf(counter));
                    counter--;
                }

                @Override
                public void onFinish() {
                    assignResults(gameType, difficulty);
                    nextScreen(gameType, difficulty);
                }
            }.start();
        } else if (gameType.equals("Quote")) {
            counter = 0;
            myCountDown = new CountDownTimer(1000000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    runGame(gameType, difficulty);
                    countDown.setText(String.valueOf(counter));
                    counter++;
                }

                @Override
                public void onFinish() {
                    assignResults(gameType, difficulty);
                    nextScreen(gameType, difficulty);
                }
            }.start();
        }
    }
    public void back(View view) {
        myCountDown.cancel(); //cancel timers and go back to the options activity
        Intent intent = new Intent(this, options.class);
        startActivity(intent);
    }

    public void nextScreen(String gameType, String difficulty) { //passes results, difficulty, and gametype to the results activity
        Intent intent = new Intent(this, results.class);
        intent.putExtra(gType, gameType);
        intent.putExtra(diff, difficulty);
        intent.putExtra(res, results);
        startActivity(intent);
    }

    public void assignResults(String gameType, String difficulty){ //calculates the results for the different gamemodes
        int correct = correctValue(); //call the function to determine how many they got right
        if(gameType.equals("Race")){
            if(correct == correctNeeded)
                results = "1"; //Success
            else
                results = "0"; //Failure
        }
        else if(gameType.equals("Timer")){
            results = String.valueOf((int)(60 * correct / 40 )); //Gets the WPM value
        }
        else if (gameType.equals("Quote")){
            results = String.valueOf(counter); //Gets how many seconds it took
        }
    }
    public int correctValue(){
        int correct = 0;
        for (int i = 0; i < userInput.length; i++){
            if((userInput[i] != null) && (correctInput[i] != null) && (i < correctInput.length) && (userInput[i].equals(correctInput[i]))) //if both variables contain an element that has been initialized, compare
                correct++;
        }
        return correct;
    }
    public void runGame(String gameType, String difficulty){ //runs every second, is n complexity with a max of around 300, so it is not resource intensive
        TextView input = findViewById(R.id.userValue);
        String text = "";
        int j = 0;
        String currentWord = "";
        if(!TextUtils.isEmpty(input.getText().toString())) { //If theres something there, get the text
            text = (String) input.getText().toString();
        }
        if(!text.equals("")) //Making sure the text is not empty
            for(int i = 0; i < text.length(); i++){
                while(i < text.length() && text.charAt(i) != ' '){ //collect characters until there is a space
                    String currentChar = Character.toString(text.charAt(i));
                    currentWord = currentWord + currentChar;
                    i++;
                }
                userInput[j] = currentWord; //put the current word in the array
                currentWord = "";
                j++;
            }
        if (gameType.equals("Race") || gameType.equals("Quote")){ //if you need to be able to leave the game early (by winning)
            int correct = correctValue();
            if(correct == correctNeeded){
                myCountDown.cancel();
                assignResults(gameType, difficulty);
                nextScreen(gameType, difficulty);
            }
        }
    }
    public void displayText(String gameType, String difficulty) { //function to get random strings in the form of an array of strings
        if(gameType.equals("Race")) {
            if (difficulty.equals("Easy")) {
                Resources res = getResources();
                String[] timerRaceEasyFull = res.getStringArray(R.array.Timer_Race_easy); //pulls from the easy word list
                correctNeeded = 15;
                for (int i = 0; i < 15; i++) {
                    int rnd = new Random().nextInt(timerRaceEasyFull.length);
                    correctInput[i] = timerRaceEasyFull[rnd];
                }
            } else if (difficulty.equals("Medium")) {
                Resources res = getResources();
                String[] timerRaceFull = res.getStringArray(R.array.Timer_Race_medium); //pulls from the medium word list
                correctNeeded = 20;
                for (int i = 0; i < 20; i++) {
                    int rnd = new Random().nextInt(timerRaceFull.length);
                    correctInput[i] = timerRaceFull[rnd];
                }
            } else if (difficulty.equals("Hard")) {
                Resources res = getResources();
                String[] timerRaceFull = res.getStringArray(R.array.Timer_Race_hard); //pulls from the hard word list
                correctNeeded = 25;
                for (int i = 0; i < 25; i++) {
                    int rnd = new Random().nextInt(timerRaceFull.length);
                    correctInput[i] = timerRaceFull[rnd];
                }
            }
        }
        else if(gameType.equals("Timer")) {
            correctNeeded = 40; //timer will always have 40 words
            if (difficulty.equals("Easy")) {
                Resources res = getResources();
                String[] timerRaceEasyFull = res.getStringArray(R.array.Timer_Race_easy); //pulls from the easy word list
                for (int i = 0; i < 40; i++) {
                    int rnd = new Random().nextInt(timerRaceEasyFull.length);
                    correctInput[i] = timerRaceEasyFull[rnd];
                }
            } else if (difficulty.equals("Medium")) {
                Resources res = getResources();
                String[] timerRaceFull = res.getStringArray(R.array.Timer_Race_medium); //pulls from the medium word list
                for (int i = 0; i < 40; i++) {
                    int rnd = new Random().nextInt(timerRaceFull.length);
                    correctInput[i] = timerRaceFull[rnd];
                }
            } else if (difficulty.equals("Hard")) {
                Resources res = getResources();
                String[] timerRaceFull = res.getStringArray(R.array.Timer_Race_hard); //pulls from the hard word list
                for (int i = 0; i < 40; i++) {
                    int rnd = new Random().nextInt(timerRaceFull.length);
                    correctInput[i] = timerRaceFull[rnd];
                }
            }
        }
        else if (gameType.equals("Quote")){ //quote takes popular quotes, not random text
            if (difficulty.equals("Easy")) { //get easy quotes
                Resources res = getResources();
                Random rn = new Random();
                int randomChoice = rn.nextInt(3) + 1;
                if (randomChoice == 1) { //get a random quote
                    correctNeeded = 29;
                    correctInput = res.getStringArray(R.array.Word_easy_1);
                }
                else if (randomChoice == 2) {
                    correctNeeded = 39;
                    correctInput = res.getStringArray(R.array.Word_easy_2);
                }
                else if (randomChoice == 3) {
                    correctNeeded = 23;
                    correctInput = res.getStringArray(R.array.Word_easy_3);
                }
            }
            else if (difficulty.equals("Medium")) { //get medium difficulty quotes
                Resources res = getResources();
                Random rn = new Random();
                int randomChoice = rn.nextInt(3) + 1;
                if (randomChoice == 1) { //get a random quote
                    correctNeeded = 22;
                    correctInput = res.getStringArray(R.array.Word_medium_1);
                }
                else if (randomChoice == 2) {
                    correctNeeded = 35;
                    correctInput = res.getStringArray(R.array.Word_medium_2);
                }
                else if (randomChoice == 3) {
                    correctNeeded = 35;
                    correctInput = res.getStringArray(R.array.Word_medium_3);
                }
            }
            else if (difficulty.equals("Hard")) { //get hard difficult quotes
                Resources res = getResources();
                Random rn = new Random();
                int randomChoice = rn.nextInt(3) + 1;
                if (randomChoice == 1) { //get a random quote
                    correctNeeded = 42;
                    correctInput = res.getStringArray(R.array.Word_hard_1);
                }
                else if (randomChoice == 2) {
                    correctNeeded = 32;
                    correctInput = res.getStringArray(R.array.Word_hard_2);
                }
                else if (randomChoice == 3) {
                    correctNeeded = 39;
                    correctInput = res.getStringArray(R.array.Word_hard_3);
                }
            }
        }
        TextView display = findViewById(R.id.textView2);
        String text = "";
        for (int i = 0; i < correctInput.length; i++){ //compile the array into a string to display
            if(correctInput[i] != null){
                text = text + correctInput[i] + " ";
            }
        }
        display.setText(text);
    }

}