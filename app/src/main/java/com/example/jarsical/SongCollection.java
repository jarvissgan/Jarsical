package com.example.jarsical;

import com.example.jarsical.data.UserDao;

public class SongCollection {

    private Song songs[] = new Song[10];
    public SongCollection(){



    }
    public int getNextSong(int currentSongIndex){
        if(currentSongIndex >= songs.length-1){
            return currentSongIndex;
        }else{
            return currentSongIndex+1;
        }
    }
    public int getPreviousSong(int currentSongIndex){
        if(currentSongIndex <= 0){
            return currentSongIndex;
        }else{
            return currentSongIndex-1;
        }
    }
    public Song getCurrentSong(int currentSongId){
        return songs[currentSongId];
    }

    public int searchSongById(String id){
        for(int index=0; index < songs.length; index++){
            Song tempSong = songs[index];
            if(tempSong.getId().equals(id)){
                return index;
            }
        }
        return  -1;
    }

}
