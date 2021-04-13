package com.digital.rewind.itemAdapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalLocalSongs;

import java.util.List;

public class itemAdapterLocalSongs extends RecyclerView.Adapter<itemAdapterLocalSongs.ViewHolder> {
    List<modalLocalSongs> local_songs_itemList;

    public itemAdapterLocalSongs(List<modalLocalSongs> local_songs_itemList) {
        this.local_songs_itemList = local_songs_itemList;

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

    }

    @Override
    public int getItemCount() {
        return local_songs_itemList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        com.google.android.material.imageview.ShapeableImageView local_song_image;
        TextView local_song_title, local_song_art_name, local_song_length;
        ImageView local_song_option;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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
