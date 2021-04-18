package com.digital.rewind.activitys;

import android.graphics.Bitmap;

public class song {
    private String songName, songUrl, songArtistName;
          String songLength;

    public song() {
    }

    public song(String songName, String songUrl, String songArtistName,String songLength) {
        this.songName = songName;
        this.songUrl = songUrl;
        this.songArtistName = songArtistName;
        this.songLength=songLength;
    }


    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongArtistName() {
        return songArtistName;
    }

    public void setSongArtistName(String songArtistName) {
        this.songArtistName = songArtistName;
    }

    public String getSongLength() {
        return songLength;
    }

    public void setSongLength(String songLength) {
        this.songLength = songLength;
    }

}