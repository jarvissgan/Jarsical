package com.example.jarsical.fragments.search;

import static com.example.jarsical.SongCollection.songs;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jarsical.R;
import com.example.jarsical.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> {

    public searchAdapter(Song[] songs) {
        this.songArray = new ArrayList<>();

        for(int i =0; i< songs.length;i++){
            songArray.add(songs[i]);
        }
    }
    private static ArrayList<Song> songArray;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_row_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songArray.get(position);
        holder.songTitle.setText(song.getTitle());
        holder.songArtist.setText(song.getArtists());
        Picasso.get().load(song.getArtLink()).fit().placeholder(R.drawable.ic_baseline_error_24).into(holder.imageButton);// loads images into imageButton
        holder.imageButton.setContentDescription(song.getId());
        holder.favButton.setContentDescription(song.getId());

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
    public void filter(String text){
        ArrayList<Song> tempArrayList = new ArrayList<>();
        Log.d("filter",tempArrayList.toString());
            for(Song song: songArray){
                if(song.getTitle().toLowerCase().contains(text)){
                    tempArrayList.add(song);
                    Log.d("filter",tempArrayList.toString());
                }
            }
            searchAdapter.filterList(tempArrayList);
    }
    public static void filterList(ArrayList<Song> songFiltered){
        songArray = songFiltered;
    }
}
