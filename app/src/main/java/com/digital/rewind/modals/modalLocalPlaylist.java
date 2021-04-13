package com.digital.rewind.modals;

public class modalLocalPlaylist {
    int localplaylistImage;
    String localnoOfSongsInPlaylist, localnameOfPlaylist;

    public modalLocalPlaylist(int localplaylistImage, String localnoOfSongsInPlaylist, String localnameOfPlaylist) {
        this.localplaylistImage = localplaylistImage;
        this.localnoOfSongsInPlaylist = localnoOfSongsInPlaylist;
        this.localnameOfPlaylist = localnameOfPlaylist;


    }

    public int getLocalplaylistImage() {
        return localplaylistImage;
    }

    public void setLocalplaylistImage(int localplaylistImage) {
        this.localplaylistImage = localplaylistImage;
    }

    public String getLocalnoOfSongsInPlaylist() {
        return localnoOfSongsInPlaylist;
    }

    public void setLocalnoOfSongsInPlaylist(String localnoOfSongsInPlaylist) {
        this.localnoOfSongsInPlaylist = localnoOfSongsInPlaylist;
    }

    public String getLocalnameOfPlaylist() {
        return localnameOfPlaylist;
    }

    public void setLocalnameOfPlaylist(String localnameOfPlaylist) {
        this.localnameOfPlaylist = localnameOfPlaylist;
    }


}