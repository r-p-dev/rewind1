package com.digital.rewind.itemAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.activitys.MainActivity;
import com.digital.rewind.activitys.PlayerActivity;
import com.digital.rewind.activitys.UploadActivity;
import com.digital.rewind.fragments.PlayFragment;
import com.digital.rewind.modals.modalLocalSongs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContextCompat.getSystemServiceName;
import static androidx.core.content.ContextCompat.startActivity;

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
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("namelist", (ArrayList) namelist);
                intent.putExtra("pathlist", (ArrayList) pathlist);
                intent.putExtra("position", position);

                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return local_songs_itemList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        com.google.android.material.imageview.ShapeableImageView local_song_image;
        TextView local_song_title, local_song_art_name, local_song_length;
        ImageView local_song_option;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout=itemView.findViewById(R.id.local_song_item);
            local_song_image = itemView.findViewById(R.id.local_song_image);
            local_song_title = itemView.findViewById(R.id.local_song_title);
            local_song_art_name = itemView.findViewById(R.id.local_song_art_name);
            local_song_length = itemView.findViewById(R.id.local_song_length);
            local_song_option = itemView.findViewById(R.id.local_songs_song_option_btn);




            local_song_option.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showLocalOption(v);
        }

        private void showLocalOption(View view) {
            PopupMenu localOptionMenu = new PopupMenu(view.getContext(), view);
            localOptionMenu.inflate(R.menu.option_menu);
            localOptionMenu.setOnMenuItemClickListener(this);
            localOptionMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.option_menu_play:

                    Toast.makeText(itemView.getContext(), "play option clicked @ in:" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.option_menu_playnext:
                    Toast.makeText(itemView.getContext(), "playNext option clicked @ in :" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.option_menu_addtoplaylist:
                    Toast.makeText(itemView.getContext(), "AddToPlayList option clicked @ in :" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        }
    }

    }

