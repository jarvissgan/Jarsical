package com.example.jarsical.fragments.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jarsical.PlaySongActivity;
import com.example.jarsical.R;
import com.example.jarsical.Song;
import com.example.jarsical.SongCollection;
import com.squareup.picasso.Picasso;

public class songAdapter extends RecyclerView.Adapter<songAdapter.ViewHolder> {
    public songAdapter(Song[] songs) {
        this.songs = songs;
    }

    Song[] songs;

    @NonNull
    @Override
    public songAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull songAdapter.ViewHolder holder, int position) {
        Song song = songs[position];
        holder.songTitle.setText(song.getTitle());
        holder.songArtist.setText(song.getArtists());
        Picasso.get().load(song.getArtLink()).fit().placeholder(R.drawable.ic_baseline_error_24).into(holder.imageButton);// loads images into imageButton

        holder.imageButton.setContentDescription(song.getId());
        holder.favButton.setContentDescription(song.getId());

        holder.imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PlaySongActivity.class);
            intent.putExtra("index",position);
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return songs.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle, songArtist;
        ImageButton imageButton;
        Button favButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            songTitle = itemView.findViewById(R.id.txtSongName);
            songArtist = itemView.findViewById(R.id.txtSongArtist);
            imageButton = itemView.findViewById(R.id.imageButton);
            favButton = itemView.findViewById(R.id.buttonTest);

        }
    }
}
