package com.example.msmusicplayer;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.util.Clock;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.Iterator;
import java.util.Queue;

public class PlayingSong {
    TextView songTitle;
    TextView songLength;
    int pos,qpos;
    private final Context context;
    public static ExoPlayer exoPlayer;
    public static int size;
    public static ArrayList<AudioFileDetails> Queue;

    public void setSize(int size) {
        PlayingSong.size=size;
    }

    public PlayingSong(Context context) {
        pos=-1;
        qpos=0;
        this.context = context;
        exoPlayer= new ExoPlayer.Builder(context).build();
        PlayingSong.Queue=new ArrayList<>();
    }

    public void setPos(int pos) {

        this.pos = pos;
    }
    public static void destroyPlayer()
    {
        exoPlayer.release();
    }
    public void playThisSong(int pos) {
        this.pos=pos;
        File audioFile = new File(MainActivity.SongDetails.get(pos).filePath);
        Uri audioUri = Uri.fromFile(audioFile);
        MediaItem songFile= MediaItem.fromUri(audioUri);
        exoPlayer.setMediaItem(songFile);
        exoPlayer.prepare();
        MainActivity.PlayButton.setImageResource(R.drawable.ic_pause_button);
        exoPlayer.play();
        MainActivity.setPlayingCardDetails(MainActivity.SongDetails.get(pos));
        QueuePage.setNowPlaying(MainActivity.SongDetails.get(pos));
        QueueGenerator queueGenerator=new QueueGenerator(true);
        queueGenerator.start();
    }
    public void playPrevious(){
        if(qpos>=0) {
            File audioFile = new File(PlayingSong.Queue.get(qpos).filePath);
            Uri audioUri = Uri.fromFile(audioFile);
            MediaItem songFile = MediaItem.fromUri(audioUri);
            System.out.println("gonna play" + PlayingSong.Queue.get(qpos).title);
            exoPlayer.setMediaItem(songFile);
            exoPlayer.play();
            MainActivity.setPlayingCardDetails(PlayingSong.Queue.get(qpos));
            QueuePage.setNowPlaying(PlayingSong.Queue.get(qpos));
            qpos++;
        }
    }
    public void Autoplay(){
        if(qpos<PlayingSong.Queue.size()) {
            File audioFile = new File(PlayingSong.Queue.get(qpos).filePath);
            Uri audioUri = Uri.fromFile(audioFile); 
            MediaItem songFile = MediaItem.fromUri(audioUri);
            System.out.println("gonna play" + PlayingSong.Queue.get(qpos).title);
            exoPlayer.setMediaItem(songFile);
            exoPlayer.play();
            MainActivity.setPlayingCardDetails(PlayingSong.Queue.get(qpos));
            QueuePage.setNowPlaying(PlayingSong.Queue.get(pos));
            qpos++;
        }
    }

    public void pause() {

        exoPlayer.pause();
    }


}
class QueueGenerator extends Thread
{
    boolean shuffle;
    QueueGenerator(boolean shuffle){
        this.shuffle=shuffle;
    }
    public void run(){
        int j=0;
        if(PlayingSong.Queue==null) {
            System.out.println("Queue null");
        }
        if(shuffle){
            if(!PlayingSong.Queue.isEmpty()){
                PlayingSong.Queue.removeAll(MainActivity.SongDetails);
            }
            PlayingSong.Queue.addAll(MainActivity.SongDetails);
            Collections.shuffle(PlayingSong.Queue);
            SystemClock.sleep(100);
        }
        else {
            PlayingSong.Queue.removeAll(MainActivity.SongDetails);
                int i=MainActivity.playingSong.pos;
                while (i<MainActivity.SongDetails.size()){
                    PlayingSong.Queue.add(MainActivity.SongDetails.get(i));
                    i++;
                    SystemClock.sleep(100);
                }
        }
        /*while (j<PlayingSong.Queue.size()){
            System.out.println(PlayingSong.Queue.get(j).title);
            j++;
            SystemClock.sleep(100);
        }*/
    }
}
