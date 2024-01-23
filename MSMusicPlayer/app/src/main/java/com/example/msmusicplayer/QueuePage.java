package com.example.msmusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class QueuePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static TextView SongName;
    public static TextView SongLength;
    public static ImageView SongPoster;
    private static AudioFileDetails nowPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue_page);
        SongPoster=findViewById(R.id.Queue_song_poster);
        SongLength=findViewById(R.id.Queue_playing_length);
        SongName=findViewById(R.id.playing_song_title);
        recyclerView=findViewById(R.id.Queue_recycler_view);
        setPlayingCardDetails();
        recyclerView.setAdapter(new SongAdapterQueue(PlayingSong.Queue));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public static void setPlayingCardDetails(){
        if (nowPlaying!=null){
            long CardLength=nowPlaying.duration;
            String CardSongName= nowPlaying.title;
            int sec= Integer.parseInt(String.valueOf((CardLength-(CardLength%1000))/1000));
            int min=(sec-(sec%60))/60;
            sec=sec%60;
            if(CardSongName.length()>30){
                QueuePage.SongName.setText(CardSongName.substring(0,30).concat("..."));
            }else {
                QueuePage.SongName.setText(CardSongName);
            }
            String str=String.valueOf(min);
            str+=":";str+=sec;
            QueuePage.SongLength.setText(str);
            if(nowPlaying.albumArt!=null){
                QueuePage.SongPoster.setImageBitmap(nowPlaying.albumArt);
            }
        }
    }

    public static void setNowPlaying(AudioFileDetails nowPlaying) {
        QueuePage.nowPlaying = nowPlaying;
    }
}