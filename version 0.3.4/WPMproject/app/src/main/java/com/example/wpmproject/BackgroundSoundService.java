package com.example.wpmproject;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

//This is the service used to make the app have music play in the background
//across all different activities.
public class BackgroundSoundService extends Service {
    private static final String TAG = null;
    MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        return null;
    }
    //the different events to make the music player react to. The music should start with the onCreate
    //of the main activity but there was issues with making it stop.
    //The current solution found it to make the music a opt in for the current session with buttons in the
    //game settings activity. Ideally it would be saved settings but keeping settings stay across sessions
    //was difficult to make.
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.pirate_music);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);

    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return 1;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {
        player.stop();
        player.release();
    }
    public void onPause() {
        player.stop();
    }
    public void onResume(){
        player.start();
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }
}
