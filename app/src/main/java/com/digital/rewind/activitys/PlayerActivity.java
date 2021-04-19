package com.digital.rewind.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalLocalSongs;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.digital.rewind.modals.modalSongs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.getIntent;
import static java.security.AccessController.getContext;

public class PlayerActivity extends AppCompatActivity {

    JcPlayerView jcplayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    int position=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        jcplayerView = findViewById(R.id.jcplayer);
        Intent inte=getIntent();
        String path,name,link;
        if (inte.hasExtra("namelist")&& inte.hasExtra("pathlist")){
         position=inte.getIntExtra("position",0);
            List<String> namelist = inte.getStringArrayListExtra("namelist");
            List<String> pathlist = inte.getStringArrayListExtra("pathlist");
            for (int i=0;i<namelist.size();i++){
             name=namelist.get(i);
             path=pathlist.get(i);
             jcAudios.add(JcAudio.createFromFilePath(name,path));
         }

        }
        else if (inte.hasExtra("namelist")&& inte.hasExtra("linklist")){
            position=inte.getIntExtra("position",0);
            List<String> namelist = inte.getStringArrayListExtra("namelist");
            List<String> linklist = inte.getStringArrayListExtra("linklist");
            for (int i=0;i<namelist.size();i++){
                name=namelist.get(i);
                link=linklist.get(i);
                jcAudios.add(JcAudio.createFromURL(name,link));
            }        }
        if (position>0){
                jcplayerView.playAudio(jcAudios.get(position));
        }else {
                jcplayerView.playAudio(jcAudios.get(0));

        }

        ImageView img=findViewById(R.id.player_image);
        img.setImageResource(R.drawable.mumu);
        jcplayerView.initPlaylist(jcAudios,null);
        jcplayerView.createNotification(R.drawable.logo);

    }


}