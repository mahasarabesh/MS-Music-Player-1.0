package com.example.msmusicplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import java.io.File;
import java.util.ArrayList;
public class SongDetailsGenerator extends AsyncTask<Void,Void,ArrayList<AudioFileDetails>> {
    public static ArrayList<String> mp3Formats;
    private static Context Con;
    public int sizeValue;
    public RecyclerView songsList;

    public SongDetailsGenerator(Context context,RecyclerView songsList) {
        Con=context;
        this.songsList = songsList;
        mp3Formats=new ArrayList<>();
        if(MainActivity.preferenceValues.retrivePreference("sizeValue").equals("NOT_FOUND")){
            sizeValue=0;
        }
        else{
            sizeValue=Integer.parseInt(MainActivity.preferenceValues.retrivePreference("sizeValue"));
        }
        if(MainActivity.preferenceValues.retrivePreference("mp3Formats").equals("NOT_FOUND")){
            mp3Formats.add("mp3");
            mp3Formats.add("wav");
        }
        else {
            String str=MainActivity.preferenceValues.retrivePreference("mp3Formats");
            String val="";int i=0;
            while(str.charAt(i)!='\0'){
                if(str.charAt(i)!=','){
                    val=val+str.charAt(i);
                }
                else {
                    mp3Formats.add(val);
                    val="";
                }
                i++;
            }
        }
    }

    @Override
    protected ArrayList<AudioFileDetails> doInBackground(Void... voids) {
        ArrayList<AudioFileDetails> audioFiles = new ArrayList<>();

        Uri internalUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        queryAudioFiles(internalUri, audioFiles);
        Uri externalUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        queryAudioFiles(externalUri, audioFiles);
        return audioFiles;
    }

    @Override
    protected void onPostExecute(ArrayList<AudioFileDetails> audioFiles) {
        MainActivity.getSongDetails(audioFiles);
        SongAdapter adapter = new SongAdapter();
        adapter.setSongDetails(audioFiles);
        songsList.setAdapter(adapter);
        songsList.setLayoutManager(new LinearLayoutManager(Con));
        PlayingSong.size = audioFiles.size();
    }

    private void queryAudioFiles(Uri uri, ArrayList<AudioFileDetails> audioFiles) {
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
        };

        ContentResolver contentResolver = Con.getContentResolver();
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        File file;
        long fileSize;
        Bitmap bitmap;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                if(isNeeded(filePath)){
                        file=new File(filePath);
                        fileSize=file.length();
                        if(sizeValue<(fileSize/(1024*1024))){
                            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                            long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                            retriever.setDataSource(filePath);
                            AudioFileDetails audioFile = new AudioFileDetails(id, title, artist, album, duration, filePath);
                            if(retriever.getEmbeddedPicture()!=null){
                                bitmap= BitmapFactory.decodeByteArray(retriever.getEmbeddedPicture(),0,retriever.getEmbeddedPicture().length);
                                audioFile.albumArt=bitmap;
                            }
                            audioFiles.add(audioFile);
                        }
               }
            }
            cursor.close();
        }
    }
    private boolean isNeeded(String filePath){
        boolean res=false;
        for(int i=0;i<mp3Formats.size();i++){
            if (filePath.endsWith(mp3Formats.get(i))) {
                res=true;
                break;
            }
        }
        return res;
    }
}




/*len=filePath.length();
                len=len-3;
                if(filePath.length()>5){
                    System.out.println(filePath.substring(len,len+3));
                }*/