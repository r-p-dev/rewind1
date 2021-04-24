package com.digital.rewind.modals;

public class modalArtist {
    String ArtistName, ArtistPic;

    public modalArtist() {
    }

    public modalArtist(String artistName, String artistPic) {
        ArtistName = artistName;
        ArtistPic = artistPic;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public String getArtistPic() {
        return ArtistPic;
    }

    public void setArtistPic(String artistPic) {
        ArtistPic = artistPic;
    }
}
