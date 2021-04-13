package com.digital.rewind.modals;

public class modalPlaylist {
    int playlistImage;
    String noOfSongsInPlaylist, nameOfPlaylist, creatorOfPlaylist;

    public modalPlaylist(int playlistImage, String noOfSongsInPlaylist, String nameOfPlaylist) {
        this.playlistImage = playlistImage;
        this.noOfSongsInPlaylist = noOfSongsInPlaylist;
        this.nameOfPlaylist = nameOfPlaylist;
    }

    public int getPlaylistImage() {
        return playlistImage;
    }

    public void setPlaylistImage(int playlistImage) {
        this.playlistImage = playlistImage;
    }

    public String getNoOfSongsInPlaylist() {
        return noOfSongsInPlaylist;
    }

    public void setNoOfSongsInPlaylist(String noOfSongsInPlaylist) {
        this.noOfSongsInPlaylist = noOfSongsInPlaylist;
    }

    public String getNameOfPlaylist() {
        return nameOfPlaylist;
    }

    public void setNameOfPlaylist(String nameOfPlaylist) {
        this.nameOfPlaylist = nameOfPlaylist;
    }

}