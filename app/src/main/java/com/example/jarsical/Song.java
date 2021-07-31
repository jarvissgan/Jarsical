package com.example.jarsical;

public class Song {

    private String id;
    private String title;
    private String artists;
    private String fileLink;
    private String artLink;
    private double songLength;
    private int drawable;

    public Song(String id, String title, String artists, String fileLink,String artLink, double songLength, int drawable){
        this.id = id;
        this.title = title;
        this.artists = artists;
        this.fileLink = fileLink;
        this.songLength = songLength;
        this.drawable = drawable;
        this.artLink = artLink;
    }

    public String getId(){return id;}
    public String getTitle(){return title;}
    public String getArtists(){return artists;}
    public String getFileLink(){return fileLink;}
    public String getArtLink(){return artLink;}

    public double getSongLength() {return songLength;}
    public int getDrawable() {return drawable;}
}
