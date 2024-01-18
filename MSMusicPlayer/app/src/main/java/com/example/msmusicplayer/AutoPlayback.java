package com.example.msmusicplayer;

import android.widget.SeekBar;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ExoPlayer;
import java.util.EventListener;

public class AutoPlayback implements Player.Listener{
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Player.Listener.super.onPlayerStateChanged(playWhenReady, playbackState);
        if(playbackState==PlayingSong.exoPlayer.STATE_ENDED){
            MainActivity.playingSong.Autoplay();
        }
    }
    public void onPositionDiscontinuity(int reason){
            MainActivity.CardSeekbar.setProgress((int)PlayingSong.exoPlayer.getCurrentPosition());
    }

}
