package com.example.msmusicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SearchPage extends AppCompatActivity {
    protected SongAdapterSearch songAdapterSearch;

    SearchView searchView;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        recyclerView=findViewById(R.id.SearchRecyclerView);
        searchView=findViewById(R.id.searchView);
        MainActivity.getSongDetails(MainActivity.SongDetails);

        setlayout();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query!=null) {
                    recyclerView.setAdapter(new SongAdapterSearch(query));

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!=null) {
                    recyclerView.setAdapter(new SongAdapterSearch(newText));
                }
                return false;
            }
        });
    }
    public void setlayout(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void play(View view){
        System.out.println(view.getContentDescription());
        MainActivity.playingSong.playThisSong(Integer.parseInt((String) view.getContentDescription()));
    }

}