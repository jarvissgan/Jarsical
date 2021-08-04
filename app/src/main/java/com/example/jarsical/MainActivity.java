package com.example.jarsical;

import  androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    SongCollection songCollection = new SongCollection();
    ArrayList<Song> favList = new ArrayList<Song>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void handleSelection(View myView){
        String resourceId = getResources().getResourceEntryName(myView.getId());
        Song currentArrayIndex = songCollection.searchSongById(resourceId);
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

    }
    public void goToFavouriteActivity(View view){

    }


}