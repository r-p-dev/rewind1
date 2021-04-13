package com.digital.rewind.itemAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalPlaylist;

import java.util.List;

public class itemAdapterPlaylist extends RecyclerView.Adapter<itemAdapterPlaylist.ViewHolder> {
    List<modalPlaylist> playlistItemList;

    public itemAdapterPlaylist(List<modalPlaylist> playlistItemList) {
        this.playlistItemList = playlistItemList;

    }

    @NonNull
    @Override
    public itemAdapterPlaylist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_playlists, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapterPlaylist.ViewHolder holder, int position) {
        holder.playlistImage.setImageResource(playlistItemList.get(position).getPlaylistImage());
        holder.playlistNoOfSongs.setText(playlistItemList.get(position).getNoOfSongsInPlaylist());
        holder.playlistName.setText(playlistItemList.get(position).getNameOfPlaylist());

    }

    @Override
    public int getItemCount() {
        return playlistItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        com.google.android.material.imageview.ShapeableImageView playlistImage;
        TextView playlistName, playlistNoOfSongs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistImage = itemView.findViewById(R.id.playlist_image);
            playlistNoOfSongs = itemView.findViewById(R.id.playlist_num_songs);
            playlistName = itemView.findViewById(R.id.playlist_playlist_name);
        }
    }
}
