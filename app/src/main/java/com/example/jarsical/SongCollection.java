package com.example.jarsical;

public class SongCollection {
    public static Song[] songs = new Song[10];
    public SongCollection(){

        Song photograph = new Song("S1001",
                "Photograph",
                "Ed Sheeran",
                "https://p.scdn.co/mp3-preview/097c7b735ceb410943cbd507a6e1dfda272fd8a8?cid=2afe87a64b0042dabf51f37318616965",
                "https://i.scdn.co/image/ab67616d0000b273ba5db46f4b838ef6027e6f96",
                4.34);
        songs[0] = photograph;

        Song bullsEye = new Song("S1002",
                "Bull's Eye",
                "Nano",
                "https://p.scdn.co/mp3-preview/3149bee27682223a558216b1cfa2a80286d93374?cid=2afe87a64b0042dabf51f37318616965",
                "https://i.scdn.co/image/ab67616d0000b273d5442a92d494c743dab3db79",
                4.34);
        songs[1] = bullsEye;

        Song legendary = new Song("S1003",
                "Legendary",
                "Skillet",
                "https://p.scdn.co/mp3-preview/cdc5f55be883a206e51a2949d1c854c2a2af584d?cid=2afe87a64b0042dabf51f37318616965",
                "https://i.scdn.co/image/ab67616d0000b273d1a0d3883bb1f28d712f7669",
                4.08);
        songs[2] = legendary;

        Song perfect = new Song("S1004",
                "Perfect",
                "Ed Sheeran",
                "https://p.scdn.co/mp3-preview/9779493d90a47f29e4257aa45bc6146d1ee9cb26?cid=2afe87a64b0042dabf51f37318616965",
                "https://i.scdn.co/image/ab67616d0000b273ba5db46f4b838ef6027e6f96",
                4.39);
        songs[3] = perfect;

        Song lostBoy = new Song("S1005",
                "Lost Boy",
                "Ruth B.",
                "https://p.scdn.co/mp3-preview/558d1395185753b2cfadfc61911eca079987e6a9?cid=2afe87a64b0042dabf51f37318616965",
                "https://i.scdn.co/image/ab67616d0000b27397e971f3e53475091dc8d707",
                4.60);
        songs[4] = lostBoy;

        Song handclap = new Song("S1006",
                "Handclap",
                "Fitz and The Tantrums",
                "https://p.scdn.co/mp3-preview/15f678a858f423b18a891cf06baf73d864c8609c?cid=2afe87a64b0042dabf51f37318616965",
                "https://i.scdn.co/image/ab67616d0000b27358c4833cc8b1a3d6e4890940",
                3.22);
        songs[5] = handclap;

        Song dearMariaCountMeIn = new Song("S1007",
                "Dear Maria, Count Me In",
                "All Time Low",
                "https://p.scdn.co/mp3-preview/ab48edf9ff294030c5371f0786c0304002511a72?cid=2afe87a64b0042dabf51f37318616965",
                "https://i.scdn.co/image/ab67616d0000b2739c62ae1d6785b06c58f32bba",
                3.05);
        songs[6] = dearMariaCountMeIn;

        Song checkYesJuliet = new Song("S1008",
                "Check Yes Juliet",
                "We The Kings",
                "https://p.scdn.co/mp3-preview/e4ef5de964a3e0966b567a9e57272b6dc8913a8e?cid=2afe87a64b0042dabf51f37318616965",
                "https://i.scdn.co/image/ab67616d0000b27339ac68a38866ea9ede734237",
                3.40);
        songs[7] = checkYesJuliet;

        Song loveStory = new Song("S1009",
                "Love Story",
                "Travis Atreo",
                "https://p.scdn.co/mp3-preview/74e96c312ea7c8e975ababaf8ade34ae1f1fd4e0?cid=2afe87a64b0042dabf51f37318616965",
                "https://i.scdn.co/image/ab67616d0000b273f43be914e4da22001bb25f9b",
                3.40);
        songs[8] = loveStory;

        Song laDevotee = new Song("S10010",
                "LA Devotee",
                "Panic! At The Disco",
                "https://p.scdn.co/mp3-preview/abc587ec092b9453d17f8f5d42729b026ea6f9d5?cid=2afe87a64b0042dabf51f37318616965",
                "https://i.scdn.co/image/ab67616d0000b27323152d9337d6c57b116ed13a",
                3.28);
        songs[9] = laDevotee;
    }
    public int getNextSong(int currentSongIndex){
        if(currentSongIndex >= songs.length-1){
            //if index is more than song.length-1, it plays the first song on the array
            return 0;
        }else{
            return currentSongIndex+1;
        }
    }
    public int getPreviousSong(int currentSongIndex){
        if(currentSongIndex <= 0){
            //if index is less than 0, it plays the last song on the array
            return songs.length-1;

        }else{
            return currentSongIndex-1;
        }
    }
    public Song getCurrentSong(int currentSongId){
        return songs[currentSongId];
    }

    public Song searchSongById(String id){
        for(int index=0; index < songs.length; index++){
            Song tempSong = songs[index];
            if(tempSong.getId().equals(id)){
                return tempSong;
            }
        }
        return null;
    }

}