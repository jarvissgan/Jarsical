package com.example.jarsical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class PlaySongActivity extends AppCompatActivity {

    MediaPlayer player = new MediaPlayer();
    SharedPreferences sharedPreferences;

    private String title = "";
    private String artist ="";
    private String fileLink ="";
    private int currentIndex = -1;

    private Button btnPlayPause;
    private SongCollection songCollection = new SongCollection();

    //turns array to list, used for shuffle
    List<Song> shuffleList  = Arrays.asList(songCollection.songs);

    TextView txtsongTitle;
    Button btnRepeat;
    Button btnShuffle;
    Button btnMore;


    Boolean repeatFlag = false;
    Boolean shuffleFlag = false;

    SeekBar seekBar;
    Handler handler = new Handler();
    ArrayList<Song> favList = new ArrayList<Song>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hides action bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_play_song);
        Bundle songData = this.getIntent().getExtras();

        btnMore = findViewById(R.id.btnMore);
        btnRepeat = findViewById(R.id.btnRepeat);
        btnShuffle = findViewById(R.id.btnShuffle);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        registerForContextMenu(btnMore);
        txtsongTitle = findViewById(R.id.txtSongTitle);

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();
            }
        });

        currentIndex = songData.getInt("index");
        Log.d("Temasek","Retrieved position is: " + currentIndex);
        displaySongBasedOnIndex(currentIndex);
        playSong(fileLink);

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

        //gets shared preferences from device
        sharedPreferences = getSharedPreferences("playlist",MODE_PRIVATE);
        String albums = sharedPreferences.getString("playlist","");
        if(!albums.equals("")){
            //if shared preferences exist, it sets favList as the contents in shared preferences
            TypeToken<ArrayList<Song>> token = new TypeToken<ArrayList<Song>>(){};
            Gson gson = new Gson();
            favList = gson.fromJson(albums,token.getType());
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //shows context menu when button is pressed
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");
        getMenuInflater().inflate(R.menu.menu_more, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_sports_mode:
                // when option_sports_mode is pressed, switches to PlaySportsModeActivity
                Intent intent = new Intent(this, PlaySportsModeActivity.class);
                intent.putExtra("index",currentIndex);
                startActivity(intent);
                player.stop();

                return true;
            case R.id.option_playlist:
                //when option_playlist is pressed, adds current song to favList and stores in shared preferences
                String songID = txtsongTitle.getText().toString();
                Log.d("songid",songID);
                Song song = songCollection.searchSongByName(songID);
                favList.add(song);

                Gson gson = new Gson();
                String json = gson.toJson(favList);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("playlist", json);
                editor.apply();
                Toast.makeText(this,"Added to playlist",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
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
            handler.postDelayed(p_bar,1000);
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
            Log.d("Temasek","Disabled repeat. Button ID:" + btnRepeat.getId());

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
            shuffleList = Arrays.asList(songCollection.songs);

            //takes the list and applies to songCollection.songs array
            shuffleList.toArray(songCollection.songs);

        }
        shuffleFlag = !shuffleFlag;
    }
}