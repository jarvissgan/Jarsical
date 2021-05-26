package com.example.jarsical;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class playSongActivity extends AppCompatActivity {

    MediaPlayer player;
    private String title = "";
    private String artist ="";
    private String fileLink ="";
    private int drawable;
    private int currentIndex = -1;

    private MediaPlayer Player = new MediaPlayer();
    private Button btnPlayPause =null;
    private SongCollection songCollection = new SongCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        Bundle songData = this.getIntent().getExtras();
        currentIndex = songData.getInt("index");
        Log.d("Temasek","Retrieved position is: " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        //playSong(fileLink);
    }
    public void displaySongBasedOnIndex(int selectedIndex){
        Song song = songCollection.getCurrentSong(currentIndex);
        title =song.getTitle();
        artist = song.getArtists();
        fileLink = song.getFileLink();
        drawable = song.getDrawable();
        TextView txtTitle = findViewById(R.id.txtSongTitle);
        txtTitle.setText(title);
        TextView txtArtist = findViewById(R.id.txtArtist);
        txtArtist.setText(artist);
        ImageView iCoverArt = findViewById(R.id.imgCoverArt);
        iCoverArt.setImageResource(drawable);
    }
    public void playSong(String songUrl){
        try{
            player.reset();
            player.setDataSource(songUrl);
            player.prepare();
            player.start();
            //gracefullyStopsWhenMusicEnds();

            btnPlayPause.setText("PAUSE");
            setTitle(title);

        }catch (IOException e){
            e.printStackTrace();


        }
    }
}