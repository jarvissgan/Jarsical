package com.example.jarsical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.room.Query;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarsical.data.User;
import com.example.jarsical.data.UserDao;
import com.example.jarsical.data.UserDatabase;

import java.io.IOException;
import java.util.List;


public class PlaySongActivity extends AppCompatActivity{

    MediaPlayer player = new MediaPlayer();
    private String title = "";
    private String artist ="";
    private String fileLink ="";
    private int drawable;
    private String currentArtLink = "";

    private String artLink = "";


    private Button btnPlayPause =null;
    private SongCollection songCollection = new SongCollection();
    Button btnRepeat;
    Boolean repeatFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        btnPlayPause = findViewById(R.id.btnPlayPause);

        //gets value from intent and adds it to currentArtLink
        Bundle songData = this.getIntent().getExtras();
        currentArtLink = songData.getString("artLink");
        Log.d("Temasek","Current art link is: " + currentArtLink);

        //obtains song data from Db
        displaySongBasedOnArtLink(currentArtLink);
        playSong(fileLink);
        btnRepeat = findViewById(R.id.btnRepeat);
    }
    public String getSongDataFromDb(String currentArtLink){


        return currentArtLink;
    }

    public void displaySongBasedOnArtLink(String artLink){
        User user = UserDao.load(currentIndex);
        title = song.getTitle();
        artist = song.getArtists();
        fileLink = song.getFileLink();
        drawable = song.getDrawable();
        TextView txtTitle = findViewById(R.id.txtSongTitle);
        txtTitle.setText(title);
        TextView txtArtist = findViewById(R.id.txtArtist);
        txtArtist.setText(artist);
//        ImageView iCoverArt = findViewById(R.id.imgCoverArt);
//        iCoverArt.setImageResource(drawable);
    }
    public void playSong(String songUrl){
        try{
            player.reset();
            player.setDataSource(songUrl);
            player.prepare();
            player.start();
            gracefullyStopsWhenMusicEnds();

            btnPlayPause.setText("PAUSE");
            setTitle(title);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void playOrPauseMusic(View view){
        if(player.isPlaying()){
            player.pause();
            btnPlayPause.setText("PLAY");
        }else{
            player.start();
            btnPlayPause.setText("PAUSE");
        }
    }
    public void playNext(View view){
        currentIndex = songCollection.getNextSong(currentIndex);
        Toast.makeText(this, "After clicking playNext,\nthe current index of this song\n"
        + "in the songCollection array is now: " + currentIndex, Toast.LENGTH_LONG).show();
        Log.d("Temasek", "After playNext, the index is now: " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);

    }

    public void playPrevious(View view){
        currentIndex = songCollection.getPreviousSong(currentIndex);
        Toast.makeText(this, "After clicking playPrevious,\nthe current index of this song\n"
                + "in the songCollection array is now: " + currentIndex, Toast.LENGTH_LONG).show();
        Log.d("Temasek", "After playNext, the index is now: " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);

    }

    private void gracefullyStopsWhenMusicEnds(){
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(repeatFlag){
                    playOrPauseMusic(null);
                }else{
                    Toast.makeText(getBaseContext(),"The song had ended and the onCompleteListener is activated\n" + "The button text is changed to 'PLAY", Toast.LENGTH_LONG).show();
                    btnPlayPause.setText("PLAY");
                }

            }
        });
    }
    public void onBackPressed(){
        super.onBackPressed();
        if(player.isPlaying()) {
            player.pause();
        }
    }

    public void repeatSong(View view) {
        if(repeatFlag){
            btnRepeat.setBackgroundResource(R.drawable.repeat_off);
            Log.d("Temasek","Activated disabled. Button ID:" + btnRepeat.getId());

        }else {
            btnRepeat.setBackgroundResource(R.drawable.repeat_on);
            Log.d("Temasek","Activated repeat. Button ID:" + btnRepeat.getId());
        }
        repeatFlag = !repeatFlag;
    }
}