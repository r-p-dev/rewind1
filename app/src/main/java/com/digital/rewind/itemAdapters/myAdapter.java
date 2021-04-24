package com.digital.rewind.itemAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.activitys.MainActivity;
import com.digital.rewind.activitys.PlayerActivity;
//import com.digital.rewind.fragments.PlayFragment;
import com.digital.rewind.modals.model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class myAdapter extends FirebaseRecyclerAdapter<model,myAdapter.myviewholder> {
    List<String> namelist=new ArrayList<>();
    List<String> linklist=new ArrayList<>();
    Context mContext;
    public myAdapter(Context context,@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
        mContext=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model)
    { Picasso.get().load(model.getImageUrl()).into(holder.img);
        holder.name.setText(model.getSongName());
        holder.art.setText(model.getSongArtist());
        holder.len.setText(model.getSongDuration());
        holder.searchSocontainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        namelist.add(model.getSongName());
                        linklist.add(model.getSongUrl());


                    Intent intent=new Intent(mContext, PlayerActivity.class);
                    Bundle b=new Bundle();
                    b.putStringArrayList("namelist", (ArrayList<String>) namelist);
                    b.putStringArrayList("linklist", (ArrayList<String>) linklist);
                    b.putInt("position",position);
                    intent.putExtras(b);
                    mContext.startActivity(intent);

                }
            });

        }



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name,art,len;
        ConstraintLayout searchSocontainer;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.ser_song_image);
            name=itemView.findViewById(R.id.ser_song_length);
            art=itemView.findViewById(R.id.ser_song_art_name);
            len=itemView.findViewById(R.id.ser_song_length);
            searchSocontainer=itemView.findViewById(R.id.ser_song_item);
        }
    }
}
