package com.digital.rewind.itemAdapters;

import android.content.Context;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.activitys.MainActivity;
import com.digital.rewind.fragments.PlayFragment;
import com.digital.rewind.modals.modalLocalSongs;

import java.util.ArrayList;
import java.util.List;

public class itemAdapterLocalSongs extends RecyclerView.Adapter<itemAdapterLocalSongs.ViewHolder> {
    List<modalLocalSongs> local_songs_itemList;
    private Context mContext;
    List<String>  namelist=new ArrayList<>();
    List<String> pathlist=new ArrayList<>();

    public itemAdapterLocalSongs(Context context,List<modalLocalSongs> local_songs_itemList) {
        this.local_songs_itemList = local_songs_itemList;
        mContext = context;
    }

    @NonNull
    @Override
    public itemAdapterLocalSongs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_songs_local, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapterLocalSongs.ViewHolder holder, int position) {
        byte[] data=local_songs_itemList.get(position).getLocalsongImage();
        if (data!=null){
            holder.local_song_image.setImageBitmap((BitmapFactory.decodeByteArray(data, 0, data.length)));
        }
        else {
            holder.local_song_image.setImageResource(R.drawable.mumu);
        }
        holder.local_song_title.setText(local_songs_itemList.get(position).getLocalsongName());
        holder.local_song_art_name.setText(local_songs_itemList.get(position).getLocalsongArt());
        holder.local_song_length.setText(local_songs_itemList.get(position).getLocalsongLength());




        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0;i<local_songs_itemList.size();i++){

                    namelist.add(local_songs_itemList.get(i).getLocalsongName());
                    pathlist.add(local_songs_itemList.get(i).getLocalsongPath());
                }

                PlayFragment pf=new PlayFragment();
                Bundle b=new Bundle();
                b.putStringArrayList("namelist", (ArrayList<String>) namelist);
                b.putStringArrayList("pathlist", (ArrayList<String>) pathlist);
                b.putInt("position",position);
                pf.setArguments(b);
                loadFragment(pf);
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        //replacing fragment

        ((MainActivity)mContext).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
        ((MainActivity)mContext).bottomNavigation.show(3, true);
        ((MainActivity)mContext).playfragment=fragment;
        ((MainActivity)mContext).activeFragment= ((MainActivity)mContext).playfragment;



    }


    @Override
    public int getItemCount() {
        return local_songs_itemList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        com.google.android.material.imageview.ShapeableImageView local_song_image;
        TextView local_song_title, local_song_art_name, local_song_length;
        ImageView local_song_option;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout=itemView.findViewById(R.id.local_song_item);
            local_song_image = itemView.findViewById(R.id.local_song_image);
            local_song_title = itemView.findViewById(R.id.local_song_title);
            local_song_art_name = itemView.findViewById(R.id.local_song_art_name);
            local_song_length = itemView.findViewById(R.id.local_song_length);
        }
    }

    }

