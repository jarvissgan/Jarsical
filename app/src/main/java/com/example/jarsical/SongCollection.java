package com.example.jarsical;

public class SongCollection {
    private Song songs[] = new Song[3];
    public SongCollection(){

        Song photograph = new Song("S1001",
                "1. Photograph",
                "Ed Sheeran",
                "https://p.scdn.co/mp3-preview/097c7b735ceb410943cbd507a6e1dfda272fd8a8?cid=2afe87a64b0042dabf51f37318616965",
                4.34,
                R.drawable.photograph);
        songs[0] = photograph;
        Song onTheFloor = new Song("S1002",
                "2. On The Floor",
                "Jennifer Lopez",
                "https://p.scdn.co/mp3-preview/097c7b735ceb410943cbd507a6e1dfda272fd8a8?cid=2afe87a64b0042dabf51f37318616965",
                //there is no link for On The Floor
                4.34,
                R.drawable.on_the_floor);
        songs[1] = onTheFloor;
    }
}
