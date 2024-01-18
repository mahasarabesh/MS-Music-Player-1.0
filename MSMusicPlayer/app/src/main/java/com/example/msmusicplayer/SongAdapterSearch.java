package com.example.msmusicplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
public class SongAdapterSearch extends RecyclerView.Adapter<SongAdapterSearch.ViewHolder>
{
    ArrayList<AudioFileDetails> SongDetails=new ArrayList<>();
    public SongAdapterSearch(String query) {
        updateDataset(query);
    }

    private void updateDataset(String query) {
        SongDetails.clear();

        for (int i = 0; i < MainActivity.SongDetails.size(); i++) {
            if (MainActivity.SongDetails.get(i).title.contains(query)) {
                SongDetails.add(MainActivity.SongDetails.get(i));
            }
        }
    }

    @NonNull
    @Override
    public SongAdapterSearch.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_unit, parent, false);
        return new SongAdapterSearch.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SongAdapterSearch.ViewHolder holder, int position) {
        holder.Songtitle.setText(SongDetails.get(position).title);
        holder.Songlength.setText(String.valueOf((SongDetails.get(position).duration) / 1000));
        holder.Songtitle.setContentDescription(String.valueOf(position));
        holder.Songlength.setContentDescription(String.valueOf(position));
        holder.layout.setContentDescription(String.valueOf(position));
        holder.SongPoster.setContentDescription(String.valueOf(position));
        if(SongDetails.get(position).albumArt!=null){
            holder.SongPoster.setImageBitmap(SongDetails.get(position).albumArt);
        }
    }

    @Override
    public int getItemCount() {
        return SongDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Songtitle;
        TextView Songlength;
        ImageView SongPoster;
        ImageView SongDots;
        MaterialCardView Cardview;
        View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SongDots= itemView.findViewById(R.id.song_three_dot);


            Songtitle= itemView.findViewById(R.id.song_title);

            SongPoster= itemView.findViewById(R.id.song_poster);
            Songlength= itemView.findViewById(R.id.Length);
            Cardview=itemView.findViewById(R.id.SongCardView);
            layout=itemView.findViewById(R.id.constraintlayout);
        }
    }
}
