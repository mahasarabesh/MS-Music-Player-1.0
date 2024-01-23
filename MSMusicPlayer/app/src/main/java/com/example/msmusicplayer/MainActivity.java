package com.example.msmusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Context context;
    public static RecyclerView songsList;
    public static PlayingSong playingSong;
    public static PreferenceValues preferenceValues;
    static TextView CardSongName;
    static TextView CardLength;
    public static SeekBar CardSeekbar;
    private Handler handler;
    public static ImageView SongPoster;
    public static ImageView PlayButton;
    public static ImageView Playing_poster;
    public static Dialog PlayingScreen;
    public static SeekBar PlayingSlider;
    public static SongDetailsGenerator songDetailsGenerator;
    private static TextView title,album,artist,duration,bitrate,path;

    public static ArrayList<AudioFileDetails> SongDetails=new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceValues=new PreferenceValues(this);
        context=this;
        CardSongName=findViewById(R.id.play_title);
        SongPoster=findViewById(R.id.playing_poster);
        CardLength=findViewById(R.id.play_length);
        CardSeekbar=findViewById(R.id.seekBar);
        songsList=findViewById(R.id.SongList);
        PlayButton= findViewById(R.id.play_button);
        PlayButton.setImageResource(R.drawable.ic_play_button);
        songDetailsGenerator=new SongDetailsGenerator(this,songsList);
        songDetailsGenerator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        playingSong = new PlayingSong(this);
        PlayingSong.exoPlayer.addListener(new AutoPlayback());
        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CardSeekbar != null) {
                    MainActivity.CardSeekbar.setProgress((int) PlayingSong.exoPlayer.getCurrentPosition());
                    if(PlayingSlider!=null){
                        MainActivity.PlayingSlider.setProgress((int) PlayingSong.exoPlayer.getCurrentPosition());
                    }
                }
                handler.postDelayed(this, 100);
            }
        }, 100);
        CardSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    PlayingSong.exoPlayer.seekTo(progress);
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    public static void setPlayingCardDetails(AudioFileDetails audioFileDetails){
        long CardLength=audioFileDetails.duration;
        String CardSongName= audioFileDetails.title;
        int sec= Integer.parseInt(String.valueOf((CardLength-(CardLength%1000))/1000));
        MainActivity.CardSeekbar.setMax((int) CardLength);
        int min=(sec-(sec%60))/60;
        sec=sec%60;
        System.out.println(min+":"+sec);
        if(CardSongName.length()>30){
            MainActivity.CardSongName.setText(CardSongName.substring(0,30).concat("..."));
        }else {
            MainActivity.CardSongName.setText(CardSongName);
        }
        String str=String.valueOf(min);
        str+=":";str+=sec;
        MainActivity.CardLength.setText(str);
        if(audioFileDetails.albumArt!=null){
            MainActivity.SongPoster.setImageBitmap(audioFileDetails.albumArt);

        }
    }
    public void searchButton(View view){
        Intent intent=new Intent(this,SearchPage.class);
        startActivity(intent);
    }
    public void playButton(View view){
        if (PlayingSong.exoPlayer.isPlaying()){
            PlayingSong.exoPlayer.pause();
            PlayButton.setImageResource(R.drawable.ic_play_button);
        }
        else if(playingSong.pos==-1){
            playingSong.playThisSong(0);
        } else if (!PlayingSong.exoPlayer.isPlaying()) {
            PlayingSong.exoPlayer.play();
            PlayButton.setImageResource(R.drawable.ic_pause_button);
        }
    }
    public void openPlayingCard(View view){
        PlayingScreen = new Dialog(this);
        PlayingScreen.setContentView(R.layout.activity_playing_screen);
        PlayingScreen.setCancelable(true);
        PlayingScreen.show();
        PlayingSlider = PlayingScreen.findViewById(R.id.playing_screen_seekbar);
        AudioFileDetails audioFileDetails = MainActivity.SongDetails.get(PlayingSong.pos);
        Playing_poster = PlayingScreen.findViewById(R.id.playing_screen_poster);
        TextView title = PlayingScreen.findViewById(R.id.playing_screen_title);
        if (PlayingSlider != null) {
            PlayingSlider.setMax((int) audioFileDetails.duration);
        }
        title.setText(audioFileDetails.title);
        if (audioFileDetails.albumArt != null) {
            Playing_poster.setImageBitmap(audioFileDetails.albumArt);
        }
    }

    public static void getSongDetails(ArrayList<AudioFileDetails> arrayList){
        SongDetails=arrayList;
    }
    public void optionsButton(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.songoption,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.removefromqueue){
                    System.out.println("remove");
                    return true;
                } else if (item.getItemId()==R.id.addtoqueue) {
                    System.out.println("add to queue");
                    System.out.println(view.getContentDescription().toString());
                    int pos=Integer.parseInt(view.getContentDescription().toString());
                    PlayingSong.Queue.add(MainActivity.playingSong.qpos,MainActivity.SongDetails.get(pos));
                    Snackbar.make(view,"Added to Queue",Snackbar.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId()==R.id.details) {
                    int pos=Integer.parseInt(view.getContentDescription().toString());
                    final Dialog detailsDialog=new Dialog(context);
                    detailsDialog.setContentView(R.layout.details_card);
                    detailsDialog.setCancelable(true);
                    detailsDialog.show();
                    System.out.println("pos:"+pos);
                    AudioFileDetails audioFileDetails=MainActivity.SongDetails.get(pos);
                    title=detailsDialog.findViewById(R.id.Detail_title);
                    System.out.println(title.getText());
                    title.setText(audioFileDetails.title);
                    album=detailsDialog.findViewById(R.id.Detail_album);
                    album.setText(audioFileDetails.album);
                    artist=detailsDialog.findViewById(R.id.Detail_artist);
                    artist.setText(audioFileDetails.artist);
                    bitrate=detailsDialog.findViewById(R.id.Detail_bitrate);
                    bitrate.setText(audioFileDetails.bitrate);
                    duration=detailsDialog.findViewById(R.id.Detail_duration);
                    long CardLength=audioFileDetails.duration;
                    int sec= Integer.parseInt(String.valueOf((CardLength-(CardLength%1000))/1000));
                    int min=(sec-(sec%60))/60;
                    sec=sec%60;
                    String durationtxt=min+":"+sec;
                    duration.setText(durationtxt);
                    path=detailsDialog.findViewById(R.id.Detail_path);
                    path.setText(audioFileDetails.filePath);
                    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    Display display = windowManager.getDefaultDisplay();
                    System.out.println(display.getWidth());
                    path.setMaxWidth(display.getWidth()-400);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void play(View view){
        System.out.println(view.getContentDescription());
        playingSong.playThisSong(Integer.parseInt((String) view.getContentDescription()));
    }
    public void settingsButton(View view){
        Intent intent=new Intent(this,Settings.class);
        startActivity(intent);
    }
    public void QueueButton(View view){
        Intent intent=new Intent(this, QueuePage.class);
        startActivity(intent);
    }
    public void playNextButton(View view){
        playingSong.Autoplay();
    }
    public void playPreviousButton(View view){
        playingSong.playPrevious();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayingSong.destroyPlayer();
    }
}