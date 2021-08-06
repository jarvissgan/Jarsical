package com.example.jarsical.fragments.search;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jarsical.PlaySongActivity;
import com.example.jarsical.R;
import com.example.jarsical.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> implements Filterable {

    final Song[] song;
    List<Song> songFiltered;

    public searchAdapter(Song[] songs) {
        this.song = songs;
        this.songArray = new ArrayList<>();

        for(int i =0; i< songs.length;i++){
            songArray.add(songs[i]);
        }
        songFiltered = songArray;
        Log.d("ini", songArray.get(0).getTitle());
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
        Song song = songFiltered.get(position);
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
        return songFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    Log.d("charstring", "empty!");

                }else {
                    Log.d("filter", "trying to filter");
                    List<Song> filteredList = new ArrayList<>();
                    for(int i = 0; i < songArray.size();i++){
                        Log.d("filter", songArray.get(i).getTitle());
                        if(songArray.get(i).getTitle().toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(songArray.get(i));
                        }
                    }
                    songFiltered = filteredList;
                    notifyDataSetChanged();


                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = songFiltered;

                Log.d("filter results", filterResults.values.toString());
                Log.d("ini", songArray.get(0).getTitle());
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                songFiltered = (ArrayList<Song>)results.values;
                setData(songFiltered);
                Log.d("publish","uh");

            }
        };

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

    public void setData(List<Song> sonGFiltered){
        this.songFiltered = sonGFiltered;
        this.notifyDataSetChanged();
    }
}
