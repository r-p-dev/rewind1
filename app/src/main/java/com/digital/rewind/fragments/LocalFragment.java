package com.digital.rewind.fragments;

import android.Manifest;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.itemAdapters.itemAdapterLocalSongs;
import com.digital.rewind.modals.modalLocalSongs;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LocalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView localSongsRecycler;
    List<modalLocalSongs> localSongs_itemlist;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean checkPermission = false;
    RelativeLayout local_song_item;

    public LocalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocalFragment newInstance(String param1, String param2) {
        LocalFragment fragment = new LocalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local, container, false);
        localSongsRecycler = view.findViewById(R.id.local_songs_recycler);
        local_song_item=view.findViewById(R.id.local_song_item);


//init data
        List<modalLocalSongs> data = localSongs_initData();
        localSongsRecycler.setAdapter(new itemAdapterLocalSongs(getContext(),data));


        return view;
    }

    private boolean validatePermision() {

        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        checkPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        checkPermission = false;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        return checkPermission;
    }

    private List<modalLocalSongs> localSongs_initData() {
        localSongs_itemlist = new ArrayList<>();
      String[] mediaProjection = {
              MediaStore.Audio.Media._ID,
              MediaStore.Audio.Media.TITLE,
              MediaStore.Audio.Media.DATA,
              MediaStore.Audio.Media.ARTIST,
              MediaStore.Audio.Media.DURATION
      };
      if (validatePermision()) {

          android.database.Cursor cursor = getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mediaProjection, null, null, MediaStore.Audio.Media.DISPLAY_NAME);
          try {
              if (cursor.moveToFirst()) {
                  long _id;
                  String name;
                  String data;
                  String artist;
                  long length;
                  byte[] art;

                  do {
                      _id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                      name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                      data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                      artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                      length = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                      {
                          if (name.startsWith("<unknown>")) {
                              name = "Unknown Title";
                          }
                          if (artist.startsWith("<unknown>")) {
                              artist = "Unknown Artist";
                          }
                         art=getAlbumArt(data);
                          localSongs_itemlist.add(new modalLocalSongs(art, _id, name, artist, data,convertDuration(length)));
                      }
                  } while (cursor.moveToNext());
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
      }

        return localSongs_itemlist;
    }




private String convertDuration(long l){
    String out;
    String seconds = String.valueOf((l % 60000) / 1000);
    String minutes = String.valueOf(l / 60000);
    out = minutes + ":" + seconds;
    return out;
}
    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriver = new MediaMetadataRetriever();
        retriver.setDataSource(uri);
        byte[] art= retriver.getEmbeddedPicture();
        retriver.release();

        return art;
    }

}

