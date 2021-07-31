package com.example.jarsical;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
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


public class PlaySongActivity extends AppCompatActivity {

    MediaPlayer player = new MediaPlayer();
    private String title = "";
    private String artist ="";
    private String fileLink ="";
    private int currentIndex = -1;

    private MediaPlayer Player = new MediaPlayer();
    private Button btnPlayPause =null;
    private SongCollection songCollection = new SongCollection();
    Button btnRepeat;
    Boolean repeatFlag = false;

    SeekBar seekBar;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        Bundle songData = this.getIntent().getExtras();
        currentIndex = songData.getInt("index");
        Log.d("Temasek","Retrieved position is: " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);

        btnRepeat = findViewById(R.id.btnRepeat);
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

        Picasso.get().load(song.getArtLink()).placeholder(R.drawable.ic_baseline_error_24).into(iCoverArt);// loads images into imageButton
        handler.removeCallbacks(p_bar);


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


            seekBar.setMax(player.getDuration());
        }else{
            //play


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
        seekBar.setProgress(0);
        seekBar.setMax(player.getDuration());
        handler.postDelayed(p_bar,1000);

    }

    public void playPrevious(View view){
        currentIndex = songCollection.getPreviousSong(currentIndex);
        seekBar.setProgress(0);
        seekBar.setMax(player.getDuration());
        handler.postDelayed(p_bar,1000);
        Toast.makeText(this, "After clicking playNext,\nthe current index of this song\n"
                + "in the songCollection array is now: " + currentIndex, Toast.LENGTH_LONG).show();
        Log.d("Temasek", "After playNext, the index is now: " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);
        seekBar.setProgress(0);
        seekBar.setMax(player.getDuration());
        handler.postDelayed(p_bar,1000);

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