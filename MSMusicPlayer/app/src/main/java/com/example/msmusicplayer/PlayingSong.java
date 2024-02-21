package com.example.msmusicplayer;
import android.content.Context;
import android.net.Uri;
import android.os.SystemClock;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
public class PlayingSong {
    public static int pos,qpos;
    public static QueueGenerator queueGenerator;
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
        queueGenerator=new QueueGenerator(false);
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
        queueGenerator.start();
    }
    public void playPrevious(){
        if(qpos>=0 && !PlayingSong.Queue.isEmpty()) {
            File audioFile = new File(PlayingSong.Queue.get(qpos).filePath);
            Uri audioUri = Uri.fromFile(audioFile);
            MediaItem songFile = MediaItem.fromUri(audioUri);
            System.out.println("gonna play" + PlayingSong.Queue.get(qpos).title);
            exoPlayer.setMediaItem(songFile);
            exoPlayer.play();
            MainActivity.setPlayingCardDetails(PlayingSong.Queue.get(qpos));
            QueuePage.setNowPlaying(PlayingSong.Queue.get(qpos));
            pos=qpos;
            qpos++;
        }
    }
    public void Autoplay(){
        if(qpos<PlayingSong.Queue.size() && !PlayingSong.Queue.isEmpty()) {
            File audioFile = new File(PlayingSong.Queue.get(qpos).filePath);
            Uri audioUri = Uri.fromFile(audioFile); 
            MediaItem songFile = MediaItem.fromUri(audioUri);
            System.out.println("gonna play" + PlayingSong.Queue.get(qpos).title);
            exoPlayer.setMediaItem(songFile);
            exoPlayer.play();
            MainActivity.setPlayingCardDetails(PlayingSong.Queue.get(qpos));
            QueuePage.setNowPlaying(PlayingSong.Queue.get(pos));
            pos=qpos;
            qpos++;
        }
    }
}
class QueueGenerator extends Thread {
    public static boolean shuffle;
    public static int loop;

    QueueGenerator(boolean shuffle){
    QueueGenerator.shuffle=shuffle;
    loop=0;
    }
    public void setLoop() {
        QueueGenerator.loop=(QueueGenerator.loop+1)%3;
        switch(QueueGenerator.loop) {
            case 0:
                MainActivity.PlayingLoop.setImageResource(R.drawable.ic_launcher_foreground);
                break;
            case 1:
                MainActivity.PlayingLoop.setImageResource(R.drawable.ic_music);
                break;
            case 2:
                MainActivity.PlayingLoop.setImageResource(R.drawable.ic_kebab_option);
                break;
        }
    }

    public static void setShuffle() {
        if (shuffle) {
            shuffle = false;
            MainActivity.PlayingShuffle.setImageResource(R.drawable.ic_launcher_foreground);
        } else {
            shuffle = true;
            MainActivity.PlayingShuffle.setImageResource(R.drawable.ic_music);
        }
    }


        public void run() {
            if (PlayingSong.Queue == null) {
                System.out.println("Queue null");
            }
            if (shuffle) {
                if (!PlayingSong.Queue.isEmpty()) {
                    PlayingSong.Queue.removeAll(MainActivity.SongDetails);
                }
                PlayingSong.Queue.addAll(MainActivity.SongDetails);
                Collections.shuffle(PlayingSong.Queue);
                SystemClock.sleep(100);
            } else {
                PlayingSong.Queue.removeAll(MainActivity.SongDetails);
                int i = PlayingSong.pos;
                while (i < MainActivity.SongDetails.size()) {
                    PlayingSong.Queue.add(MainActivity.SongDetails.get(i));
                    i++;
                    SystemClock.sleep(100);
                }
            }
        }
    }


