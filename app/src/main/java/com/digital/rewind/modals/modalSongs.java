package com.digital.rewind.modals;

public class modalSongs {
    String songName,songUrl,imageUrl,songArtist,songDuration;

    public modalSongs() {
    }

    public modalSongs(String songName, String songUrl, String imageUrl, String songArtist, String songDuration) {
        this.songName = songName;
        this.songUrl = songUrl;
        this.imageUrl = imageUrl;
        this.songArtist = songArtist;
        this.songDuration = songDuration;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }
}

