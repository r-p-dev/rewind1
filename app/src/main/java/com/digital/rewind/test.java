//package com.digital.rewind;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.AsyncTask;
//import android.view.View;
//
//import androidx.core.content.ContextCompat;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.TimerTask;
//
//public class test {
//    private ArrayList<HashMap<String, Object>> musicData;
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1) {
//            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
//                    scanMedia();
//                } else {
//                    scanMedia();
//                }
//            } else {
//                scanMedia();
//            }
//        }
//    }
//
//    {
//    }
//
//    @Override
//    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
//        super.onActivityResult(_requestCode, _resultCode, _data);
//        switch (_requestCode) {
//            default:
//                break;
//        }
//    }
//
//    public void scanMedia () {
//        (new MediaScanTask()).execute();
//    }
//
//    private class MediaScanTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            if (musicData != null) {
//                musicData.clear();
//            }
//            loadanim.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected Void doInBackground(Void... path) {
//            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//                    && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
//                String[] mediaProjection = {
//                        android.provider.MediaStore.Audio.Media._ID,
//                        android.provider.MediaStore.Audio.Media.ARTIST,
//                        android.provider.MediaStore.Audio.Media.DATA,
//                        android.provider.MediaStore.Audio.Media.TITLE,
//                        android.provider.MediaStore.Audio.Media.ALBUM_ID
//                };
//                String orderBy = " " + android.provider.MediaStore.MediaColumns.DISPLAY_NAME;
//                android.database.Cursor cursor = getApplicationContext().getContentResolver().query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mediaProjection, null, null, orderBy);
//                try {
//                    if (cursor.moveToFirst()) {
//                        long _id;
//                        String name;
//                        String data;
//                        String artist;
//                        do {
//                            _id = cursor.getLong(cursor.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media._ID));
//                            name = cursor.getString(cursor.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.TITLE));
//                            data = cursor.getString(cursor.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.DATA));
//                            artist = cursor.getString(cursor.getColumnIndexOrThrow(android.provider.MediaStore.Audio.Media.ARTIST));
//                            {
//
//                                HashMap<String, Object> songDetails = new HashMap<>();
//                                if (name.startsWith("<unknown>")) {
//                                    name = "Unknown Title";
//                                }
//                                if (artist.startsWith("<unknown>")) {
//                                    artist = "Unknown Artist";
//                                }
//                                songDetails.put("songTitle", name);
//                                songDetails.put("songData", StringUtil.encodeString(data));
//                                songDetails.put("songArtist", artist);
//                                songDetails.put("id", _id);
//                                musicData.add(songDetails);
//                            }
//                        } while (cursor.moveToNext());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//        }
//
//        @Override
//        protected void onPostExecute(Void param){
//            if (savedData.contains("savedMusicData")) {
//                ArrayList<HashMap<String, Object>> tempMusicData = ListUtil.getArrayListFromSharedJSON(savedData, "savedMusicData");
//                if (musicData.size() > tempMusicData.size()) {
//                    savedData.edit().putString("savedMusicData", ListUtil.setArrayListToSharedJSON(musicData)).apply();
//                }
//                loadanim.setVisibility(View.GONE);
//            } else {
//                savedData.edit().putString("savedMusicData", ListUtil.setArrayListToSharedJSON(musicData)).apply();
//            }
//            timerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            intent.setClass(getApplicationContext(), SplashActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });
//                }
//            };
//            timer.schedule(timerTask, (int)(1500));
//        }
//    }
//
//
//
//
//
//
//}
