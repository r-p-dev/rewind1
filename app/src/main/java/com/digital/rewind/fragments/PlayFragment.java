package com.digital.rewind.fragments;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalLocalSongs;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.getIntent;
import static android.content.Intent.makeMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment {
    JcPlayerView jcplayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    int position=0;
  Boolean  checkPermission=false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayFragment newInstance(String param1, String param2) {
        PlayFragment fragment = new PlayFragment();
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
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        jcplayerView = view.findViewById(R.id.jcplayer);
        Bundle inte=getArguments();
        String path,name,link;
        if (inte!=null){

            if (inte.containsKey("pathlist")){
                position=inte.getInt("position",0);
                List<String> namelist = inte.getStringArrayList("namelist");
                List<String> pathlist = inte.getStringArrayList("pathlist");
                for (int i=0;i<namelist.size();i++){
                    name=namelist.get(i);
                    path=pathlist.get(i);
                    jcAudios.add(JcAudio.createFromFilePath(name,path));
                }

            }
            else if (inte.containsKey("linklist")){
                position=inte.getInt("position",0);
                List<String> namelist = inte.getStringArrayList("namelist");
                List<String> linklist = inte.getStringArrayList("linklist");
                for (int i=0;i<namelist.size();i++){
                    name=namelist.get(i);
                    link=linklist.get(i);
                    jcAudios.add(JcAudio.createFromURL(name,link));
                }
            }
            if (position>0){
                jcplayerView.playAudio(jcAudios.get(position));
            }else {
                jcplayerView.playAudio(jcAudios.get(0));

            }

            jcplayerView.initPlaylist(jcAudios,null);
            jcplayerView.createNotification(R.drawable.logo);

        }
//        else if(jcAudios.isEmpty()) {
//                String[] mediaProjection = {
//                        MediaStore.Audio.Media._ID,
//                        MediaStore.Audio.Media.TITLE,
//                        MediaStore.Audio.Media.DATA,
//                        MediaStore.Audio.Media.ARTIST,
//                        MediaStore.Audio.Media.DURATION
//                };
//                if (validatePermision()) {
//
//                    android.database.Cursor cursor = getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mediaProjection, null, null, MediaStore.Audio.Media.DISPLAY_NAME);
//                    try {
//                        if (cursor.moveToFirst()) {
//                            String songname;
//                            String data;
//
//                            do {
//                                songname = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//                                data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//                                jcAudios.add(JcAudio.createFromFilePath(songname,data));
//                            } while (cursor.moveToNext());
//                            jcplayerView.initPlaylist(jcAudios,null);
//                            jcplayerView.playAudio(jcAudios.get(0));
//                            jcplayerView.createNotification(R.drawable.logo);
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }



        ImageView img=view.findViewById(R.id.player_image);
        img.setImageResource(R.drawable.mumu);





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

}

