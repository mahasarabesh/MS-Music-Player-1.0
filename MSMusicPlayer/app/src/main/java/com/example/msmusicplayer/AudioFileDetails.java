package com.example.msmusicplayer;

import android.graphics.Bitmap;
public class AudioFileDetails {
    public final long id;
    public final String title;
    public final String artist;
    public final String album;
    public final long duration;
    public final String filePath;
    public Bitmap albumArt;

    public AudioFileDetails(long id, String title, String artist, String album, long duration, String filePath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        this.filePath = filePath;
        albumArt=null;
    }

}