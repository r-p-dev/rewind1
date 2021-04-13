package com.digital.rewind.modals;

public class modalSongs {
    int songImage;
    String songName, songArt, songLength;

    public modalSongs(int songImage, String songName, String songArt, String songLength) {
        this.songImage = songImage;
        this.songName = songName;
        this.songArt = songArt;
        this.songLength = songLength;
    }


    public int getSongImage() {
        return songImage;
    }

    public void setSongImage(int songImage) {
        this.songImage = songImage;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongArt() {
        return songArt;
    }

    public void setSongArt(String songArt) {
        this.songArt = songArt;
    }

    public String getSongLength() {
        return songLength;
    }

    public void setSongLength(String songLength) {
        this.songLength = songLength;
    }
}
