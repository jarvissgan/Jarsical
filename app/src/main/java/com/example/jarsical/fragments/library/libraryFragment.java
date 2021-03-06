package com.example.jarsical.fragments.library;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jarsical.R;
import com.example.jarsical.Song;
import com.example.jarsical.SongCollection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class libraryFragment extends Fragment {
    RecyclerView recyclerView;
    List<Song> song;
    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflates layout fragment_song and uses recyclerview to display data
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewLibrary);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        sharedPreferences = getContext().getSharedPreferences("playlist", MODE_PRIVATE);
        //checks if shared preferences exist
        String albums = sharedPreferences.getString("playlist","");
        if(!albums.equals("")){
            //if shared preferences exist, sets song as array from shared preferences
            TypeToken<ArrayList<Song>> token = new TypeToken<ArrayList<Song>>(){};
            Gson gson = new Gson();
            song = gson.fromJson(albums,token.getType());
        }
        //passes song into constructor of libraryAdapter
        recyclerView.setAdapter(new LibraryAdapter(song));
        return view;
    }
}
