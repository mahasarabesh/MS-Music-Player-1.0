package com.example.msmusicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class PlayingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_screen);
    }
    public void playNextButton(View view){
        MainActivity.playingSong.Autoplay();
    }
    public void playPreviousButton(View view){
        MainActivity.playingSong.playPrevious();
    }
}
