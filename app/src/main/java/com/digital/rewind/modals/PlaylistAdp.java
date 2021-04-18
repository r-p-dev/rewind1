package com.digital.rewind.modals;

import java.lang.reflect.Array;
import java.util.List;

public class PlaylistAdp {
    String playlistimage,playlistName,playlistCreator;
   List<String> songs;

    public PlaylistAdp() {
    }

    public PlaylistAdp(String playlistimage, String playlistName, String playlistCreator, List<String> songs) {
        this.playlistimage = playlistimage;
        this.playlistName = playlistName;
        this.playlistCreator = playlistCreator;
        this.songs = songs;
    }

    public String getPlaylistimage() {
        return playlistimage;
    }

    public void setPlaylistimage(String playlistimage) {
        this.playlistimage = playlistimage;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistCreator() {
        return playlistCreator;
    }

    public void setPlaylistCreator(String playlistCreator) {
        this.playlistCreator = playlistCreator;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }
}
