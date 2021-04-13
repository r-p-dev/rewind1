package com.digital.rewind.itemAdapters;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalSongs;

import java.util.List;

public class itemAdapterSongs extends RecyclerView.Adapter<itemAdapterSongs.ViewHolder> {
    List<modalSongs> songs_itemList;

    public itemAdapterSongs(List<modalSongs> songs_itemList) {
        this.songs_itemList = songs_itemList;

    }

    @NonNull
    @Override
    public itemAdapterSongs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_songs, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapterSongs.ViewHolder holder, int position) {
        holder.songs_song_image.setImageResource(songs_itemList.get(position).getSongImage());
        holder.songs_song_title.setText(songs_itemList.get(position).getSongName());
        holder.songs_song_art_name.setText(songs_itemList.get(position).getSongArt());
        holder.songs_song_length.setText(songs_itemList.get(position).getSongLength());

    }

    @Override
    public int getItemCount() {
        return songs_itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        com.google.android.material.imageview.ShapeableImageView songs_song_image;
        TextView songs_song_title, songs_song_art_name, songs_song_length;
        ImageView songs_song_option;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songs_song_image = itemView.findViewById(R.id.songs_song_image);
            songs_song_title = itemView.findViewById(R.id.songs_song_title);
            songs_song_art_name = itemView.findViewById(R.id.songs_song_art_name);
            songs_song_length = itemView.findViewById(R.id.songs_song_length);
            songs_song_option = itemView.findViewById(R.id.songs_song_option_btn);


            songs_song_option.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showOption(v);
        }

        private void showOption(View view) {
            PopupMenu optionMenu = new PopupMenu(view.getContext(), view);
            optionMenu.inflate(R.menu.option_menu);
            optionMenu.setOnMenuItemClickListener(this);
            optionMenu.show();
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
