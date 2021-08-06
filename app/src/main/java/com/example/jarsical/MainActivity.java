package com.example.jarsical;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jarsical.fragments.home.songFragment;
import com.example.jarsical.fragments.library.libraryFragment;
import com.example.jarsical.fragments.search.searchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    SongCollection songCollection = new SongCollection();
    ArrayList<Song> favList = new ArrayList<Song>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //fragments
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,new songFragment()).commit();
        TextView fragmentName = findViewById(R.id.txtFragmentName);
        fragmentName.setText("Home");

        //library items
        sharedPreferences = getSharedPreferences("playlist",MODE_PRIVATE);
        String albums = sharedPreferences.getString("playlist","");
        if(!albums.equals("")){
            TypeToken<ArrayList<Song>> token = new TypeToken<ArrayList<Song>>(){};
            Gson gson = new Gson();
            favList = gson.fromJson(albums,token.getType());
        }
    }
    public void handleSelection(View myView){
        String resourceId = myView.getContentDescription().toString();
        Song currentArrayIndex = songCollection.searchSongById(resourceId);
        Log.d("currentArrayIndex",currentArrayIndex.toString());
        Log.d("Temasek","The id of the pressed ImageButton is: " + resourceId);
        sendDataToActivity(currentArrayIndex);
    }
    public void sendDataToActivity(Song index){
        Intent intent = new Intent(this, PlaySongActivity.class);
        intent.putExtra("index", index.getId());
        startActivity(intent);
    }
    public void addToFavourite(View view){
        String songID = view.getContentDescription().toString();
        Song song = songCollection.searchSongById(songID);
        favList.add(song);

        Gson gson = new Gson();
        String json = gson.toJson(favList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("playlist", json);
        editor.apply();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            TextView fragmentName;

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new songFragment();
                    fragmentName = findViewById(R.id.txtFragmentName);
                    fragmentName.setText("Home");
                    break;
                case R.id.nav_library:
                    selectedFragment = new libraryFragment();
                    fragmentName = findViewById(R.id.txtFragmentName);
                    fragmentName.setText("Library");
                    break;
                case R.id.nav_search:
                    selectedFragment = new searchFragment();
                    fragmentName = findViewById(R.id.txtFragmentName);
                    fragmentName.setText("Search");
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,selectedFragment).commit();
            return true;
        }
    };


}