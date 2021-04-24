package com.digital.rewind.activitys;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalSongs;
import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.service.JcPlayerService;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {
    JcPlayerView jcplayerView;
   ArrayList<JcAudio> jcAudios = new ArrayList<>();
    int position=0;
    List<String> namelist,linklist,pathlist,songlist;
    @Override
    public void onStart() {
        super.onStart();
        jcplayerView.pause();
        Intent it = getIntent();
        if (!it.getExtras().isEmpty()){
            position=it.getIntExtra("position",0);
            System.out.println("--------------------------------------------------------------------------");
            System.out.println(position);
            if ((it.hasExtra("namelist"))&& (it.hasExtra("linklist"))){
                namelist=it.getStringArrayListExtra("namelist");
                linklist=it.getStringArrayListExtra("linklist");
                for (int i=0; i<linklist.size();i++){
                    String name=namelist.get(i);
                    String link=linklist.get(i);
                    jcAudios.add(JcAudio.createFromURL(name,link));
                }
            }
            else if ((it.hasExtra("namelist"))&& (it.hasExtra("pathlist"))){
                namelist=it.getStringArrayListExtra("namelist");
                pathlist=it.getStringArrayListExtra("pathlist");
                for (int i=0; i<pathlist.size();i++){
                    String name=namelist.get(i);
                    System.out.println("--------------------------------------------------------------------------");
                    System.out.println(name);
                    String path=pathlist.get(i);
                    System.out.println("--------------------------------------------------------------------------");
                    System.out.println(path);
                    jcAudios.add(JcAudio.createFromFilePath(name,path));
                }
            }
                else {
                Toast.makeText(this, "No songs loaded", Toast.LENGTH_SHORT).show();
            }
            if (position>0){
                jcplayerView.playAudio(jcAudios.get(position));
            }else {
                jcplayerView.playAudio(jcAudios.get(0));

            }
            jcplayerView.initPlaylist(jcAudios, null) ;
            jcplayerView.createNotification(R.drawable.logo);
        }else {
            Toast.makeText(this, "No songs loaded", Toast.LENGTH_SHORT).show();

        }


    }

    private void findsongdata(String s) {
        Query qery=FirebaseDatabase.getInstance().getReference("Songs/-"+s);
        qery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modalSongs data = snapshot.getValue(modalSongs.class);
                System.out.println("playerrr--------------song detail--------------------------------------------------------");
                System.out.println(data.getSongName());
                System.out.println(data.getSongUrl());
                String name=data.getSongName();
                String link=data.getSongUrl();
                jcAudios.add(JcAudio.createFromURL(name,link));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        jcplayerView = findViewById(R.id.jcplayer);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(PlayerActivity.this,MainActivity.class);

    }
}