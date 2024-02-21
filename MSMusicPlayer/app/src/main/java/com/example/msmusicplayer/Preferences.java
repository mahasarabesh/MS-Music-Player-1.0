package com.example.msmusicplayer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Preferences {
    private Context context;
    SharedPreferences sharedPreferences;
    public Preferences(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        int MB=sharedPreferences.getInt("SizeMB",1);
        //size
        Settings.sizeVal.setText(MB);
        //audioFormats
        Set<String> AudioFormats=new HashSet<>();
        AudioFormats.add("mp3");AudioFormats.add("wav");
        AudioFormats=sharedPreferences.getStringSet("AudioFormats",AudioFormats);
        for(int i=0;i<Settings.AudioFormat.size();i++){
            if(AudioFormats.contains(Settings.AudioFormat.get(i).getText().toString())){
                Settings.AudioFormat.get(i).setChecked(true);
            }
            else {
                Settings.AudioFormat.get(i).setChecked(false);
            }
        }
        //length
        String LengthSpinner=sharedPreferences.getString("LengthSpinner","sec");
        int length=sharedPreferences.getInt("LengthInput",30);
        if(LengthSpinner.equals("sec")){
            SongDetailsGenerator.minLength=length;
        }
        else{
            SongDetailsGenerator.minLength=length*60;
        }
        String SleepSpinner=sharedPreferences.getString("SleepSpinner","none");
        int sleepTime=sharedPreferences.getInt("sleepTime",-1);
        if(sleepTime>0){
            Settings.SleepInput.setText(sleepTime);
        }

    }
}
