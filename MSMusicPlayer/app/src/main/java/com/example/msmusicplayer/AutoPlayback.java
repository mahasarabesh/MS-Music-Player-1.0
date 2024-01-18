package com.example.msmusicplayer;


import com.google.android.exoplayer2.Player;


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
