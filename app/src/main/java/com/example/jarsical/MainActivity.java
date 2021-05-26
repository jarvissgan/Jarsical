package com.example.jarsical;

import  androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    SongCollection songCollection = new SongCollection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void handleSelection(View myView){
        String resourceId = getResources().getResourceEntryName(myView.getId());
        int currentArrayIndex = songCollection.searchSongById(resourceId);
        Log.d("Temasek","The id of the pressed ImageButton is: " + resourceId);
        sendDataToActivity(currentArrayIndex);
    }
    public void sendDataToActivity(int index){
        Intent intent = new Intent(this, playSongActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }


}