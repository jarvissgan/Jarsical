package com.example.jarsical.fragments.library;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jarsical.PlayPlaylistActivity;
import com.example.jarsical.PlaySongActivity;
import com.example.jarsical.R;
import com.example.jarsical.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    public LibraryAdapter(List<Song> playlist) {
        this.playlist = playlist;
    }
    List<Song> playlist;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_row_library,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(playlist.size()>=1){
            Song song = playlist.get(position);
            holder.songTitle.setText(song.getTitle());
            holder.songArtist.setText(song.getArtists());
            Picasso.get().load(song.getArtLink()).fit().placeholder(R.drawable.ic_baseline_error_24).into(holder.imageButton);//Uses picasso to download images into imageButton
            holder.imageButton.setContentDescription(song.getId());

            holder.imageButton.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), PlayPlaylistActivity.class);
                intent.putExtra("index",position);
                v.getContext().startActivity(intent);
            });
        }else{
        }
    }

    @Override
    public int getItemCount() {
        if(playlist == null){
            return 0;
        } else {
            return playlist.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle, songArtist;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            songTitle = itemView.findViewById(R.id.txtSongName);
            songArtist = itemView.findViewById(R.id.txtSongArtist);
            imageButton = itemView.findViewById(R.id.imageButton);

        }
    }
}
