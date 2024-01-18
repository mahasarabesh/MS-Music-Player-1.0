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
public class SongAdapterQueue extends RecyclerView.Adapter<SongAdapterQueue.ViewHolder>{
    ArrayList<AudioFileDetails> SongDetails=new ArrayList<>();
    int pos;

    public SongAdapterQueue(ArrayList<AudioFileDetails> songDetails) {
        SongDetails = songDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.song_unit,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (SongDetails.get(position).title.length() > 30) {
                holder.Songtitle.setText(SongDetails.get(position).title.substring(0, 30).concat("..."));
            } else {
                holder.Songtitle.setText(SongDetails.get(position).title);
            }
            String pos = String.valueOf(position);
            holder.Songlength.setText(String.valueOf((SongDetails.get(position).duration) / 1000));
            holder.Songtitle.setContentDescription(pos);
            holder.Songlength.setContentDescription(pos);
            holder.layout.setContentDescription(pos);
            holder.SongPoster.setContentDescription(pos);
            if (SongDetails.get(position).albumArt != null) {
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
        MaterialCardView Cardview;
        View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Songtitle= itemView.findViewById(R.id.song_title);
            SongPoster= itemView.findViewById(R.id.song_poster);
            Songlength= itemView.findViewById(R.id.Length);
            Cardview=itemView.findViewById(R.id.SongCardView);
            layout=itemView.findViewById(R.id.constraintlayout);
        }
    }
}
