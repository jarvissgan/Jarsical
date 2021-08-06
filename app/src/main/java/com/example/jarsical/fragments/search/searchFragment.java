package com.example.jarsical.fragments.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jarsical.R;
import com.example.jarsical.Song;
import com.example.jarsical.SongCollection;
import com.example.jarsical.fragments.home.songAdapter;

public class searchFragment extends Fragment {

    RecyclerView recyclerView;
    Song[] song = SongCollection.songs;
    EditText editText;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSearch);
        SearchView searchView = view.findViewById(R.id.searchView);
        searchAdapter _searchAdapter = new searchAdapter(song);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                _searchAdapter.getFilter().filter(newText);
                _searchAdapter.notifyDataSetChanged();
                Log.d("searchView", newText);
                return true;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(_searchAdapter);



        return view;
    }
}
