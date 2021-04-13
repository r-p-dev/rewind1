package com.digital.rewind.modals;

import android.graphics.Bitmap;

public class modalLocalSongs {
    byte[] LocalsongImage;
            long LocalsongId;
    String LocalsongName, LocalsongArt, LocalsongPath, LocalsongLength;

    public modalLocalSongs() {
    }

    public modalLocalSongs(byte[] localsongImage, long localsongId, String localsongName, String localsongArt, String localsongPath, String localsongLength) {
        LocalsongImage = localsongImage;
        LocalsongId = localsongId;
        LocalsongName = localsongName;
        LocalsongArt = localsongArt;
        LocalsongPath = localsongPath;
        LocalsongLength = localsongLength;
    }

    public byte[] getLocalsongImage() {
        return LocalsongImage;
    }

    public void setLocalsongImage(byte[] localsongImage) {
        LocalsongImage = localsongImage;
    }

    public long getLocalsongId() {
        return LocalsongId;
    }

    public void setLocalsongId(long localsongId) {
        LocalsongId = localsongId;
    }

    public String getLocalsongName() {
        return LocalsongName;
    }

    public void setLocalsongName(String localsongName) {
        LocalsongName = localsongName;
    }

    public String getLocalsongArt() {
        return LocalsongArt;
    }

    public void setLocalsongArt(String localsongArt) {
        LocalsongArt = localsongArt;
    }

    public String getLocalsongPath() {
        return LocalsongPath;
    }

    public void setLocalsongPath(String localsongPath) {
        LocalsongPath = localsongPath;
    }

    public String getLocalsongLength() {
        return LocalsongLength;
    }

    public void setLocalsongLength(String localsongLength) {
        LocalsongLength = localsongLength;
    }
}
