package com.example.jarsical;

public class SongCollection {
    private Song songs[] = new Song[3];
    public SongCollection(){

        Song photograph = new Song("S1001",
                "Photograph",
                "Ed Sheeran",
                "https://p.scdn.co/mp3-preview/097c7b735ceb410943cbd507a6e1dfda272fd8a8?cid=2afe87a64b0042dabf51f37318616965",
                4.34,
                R.drawable.photograph);
        songs[0] = photograph;
        Song onTheFloor = new Song("S1002",
                "On The Floor",
                "Jennifer Lopez",
                "https://p.scdn.co/mp3-preview/3149bee27682223a558216b1cfa2a80286d93374?cid=2afe87a64b0042dabf51f37318616965",
                4.34,
                R.drawable.on_the_floor);
        songs[1] = onTheFloor;
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
