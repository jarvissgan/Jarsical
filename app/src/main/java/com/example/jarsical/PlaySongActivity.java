package com.example.jarsical;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class PlaySongActivity extends AppCompatActivity {

    MediaPlayer player = new MediaPlayer();
    private String title = "";
    private String artist ="";
    private String fileLink ="";
    private int currentIndex = -1;

    private MediaPlayer Player = new MediaPlayer();
    private Button btnPlayPause =null;
    private SongCollection songCollection = new SongCollection();

    //turns array to list, used for shuffle
    List<Song> shuffleList  = Arrays.asList(songCollection.songs);

    Button btnRepeat;
    Button btnShuffle;

    Boolean repeatFlag = false;
    Boolean shuffleFlag = false;

    SeekBar seekBar;
    Handler handler = new Handler();
    ArrayList<Song> song;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hides action bar
        getSupportActionBar().hide();

        setContentView(R.layout.activity_play_song);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        Bundle songData = this.getIntent().getExtras();

        currentIndex = songData.getInt("index");
        Log.d("Temasek","Retrieved position is: " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);

        btnRepeat = findViewById(R.id.btnRepeat);
        btnShuffle = findViewById(R.id.btnShuffle);
        seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(player !=null && player.isPlaying()){
                    player.seekTo(seekBar.getProgress());
                }
            }
        });

        seekBar.setMax(player.getDuration());
        handler.postDelayed(p_bar,1000);

    }

    Runnable p_bar = new Runnable() {
        @Override
        public void run() {
            if(player !=null && player.isPlaying()){
                seekBar.setProgress(player.getCurrentPosition());
                handler.postDelayed(this,1000);
            }

        }
    };

    public void displaySongBasedOnIndex(int currentIndex){
        Song song = songCollection.getCurrentSong(currentIndex);
        title = song.getTitle();
        artist = song.getArtists();
        fileLink = song.getFileLink();
        TextView txtTitle = findViewById(R.id.txtSongTitle);
        txtTitle.setText(title);
        TextView txtArtist = findViewById(R.id.txtArtist);
        txtArtist.setText(artist);
        ImageView iCoverArt = findViewById(R.id.imgCoverArt);

        //picasso allows to download images from the image and store in cache, reducing app size
        //.fit() allows picasso to internally reduce the image size
        Picasso.get().load(song.getArtLink()).fit().placeholder(R.drawable.ic_baseline_error_24).into(iCoverArt);// loads images into imageButton
        handler.removeCallbacks(p_bar);


    }
    public void playSong(String songUrl){
        try{
            player.reset();
            player.setDataSource(songUrl);
            player.prepare();
            player.start();
            gracefullyStopsWhenMusicEnds();
            btnPlayPause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);
            setTitle(title);

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void playOrPauseMusic(View view){

        if(player.isPlaying()){
            player.pause();
            btnPlayPause.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24);




            seekBar.setMax(player.getDuration());
        }else{
            //play
            player.start();
            btnPlayPause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);

        }
    }
    public void playNext(View view){
        currentIndex = songCollection.getNextSong(currentIndex);

        /*Toast.makeText(this, "After clicking playNext,\nthe current index of this song\n"
        + "in the songCollection array is now: " + currentIndex, Toast.LENGTH_LONG).show();*/
        //Log.d("Temasek", "After playNext, the index is now: " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);
        seekBar.setProgress(0);
        seekBar.setMax(player.getDuration());
        handler.postDelayed(p_bar,1000);

    }

    public void playPrevious(View view){
        currentIndex = songCollection.getPreviousSong(currentIndex);
        seekBar.setProgress(0);
        seekBar.setMax(player.getDuration());
        handler.postDelayed(p_bar,1000);
        /*Toast.makeText(this, "After clicking playNext,\nthe current index of this song\n"
                + "in the songCollection array is now: " + currentIndex, Toast.LENGTH_LONG).show();*/
        Log.d("Temasek", "After playNext, the index is now: " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);
        seekBar.setProgress(0);
        seekBar.setMax(player.getDuration());
        handler.postDelayed(p_bar,1000);

    }

    private void gracefullyStopsWhenMusicEnds(){
        player.setOnCompletionListener(mp -> {

            if(repeatFlag){
                playOrPauseMusic(null);
            }else{
                /*Toast.makeText(getBaseContext(),"The song had ended and the onCompleteListener is activated\n" + "The button text is changed to 'PLAY", Toast.LENGTH_LONG).show();*/
                playNext(null);
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
            Log.d("Temasek","Disabled Shuffle. Button ID:" + btnRepeat.getId());

        }else {
            btnRepeat.setBackgroundResource(R.drawable.repeat_on);
            Log.d("Temasek","Activated repeat. Button ID:" + btnRepeat.getId());
        }
        repeatFlag = !repeatFlag;
    }

    public void shuffleSong(View view) {
        if(shuffleFlag){
            btnShuffle.setBackgroundResource(R.drawable.shuffle_off);
            Log.d("Temasek","Disabled Shuffle. Button ID:" + btnShuffle.getId());

        }else {
            btnShuffle.setBackgroundResource(R.drawable.shuffle_on);
            Log.d("Temasek","Activated shuffle. Button ID:" + btnShuffle.getId());
            Collections.shuffle(shuffleList);

            //takes the list and applies to songCollection.songs array
            shuffleList.toArray(songCollection.songs);

        }
        shuffleFlag = !shuffleFlag;
    }
}