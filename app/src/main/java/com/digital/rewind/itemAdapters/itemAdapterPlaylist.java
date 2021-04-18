package com.digital.rewind.itemAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalPlaylist;
import com.squareup.picasso.Picasso;

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
        Picasso.get().load(playlistItemList.get(position).getPlaylistimage()).into(holder.playlistImage);
        holder.playlistName.setText(playlistItemList.get(position).getPlaylistName());

        if (playlistItemList.get(position).getSongs()==null){
            holder.playlistNoOfSongs.setText("No");

        }else {
            List count=playlistItemList.get(position).getSongs();
            String numb= String.valueOf(count.size());
            holder.playlistNoOfSongs.setText(numb);
        }


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
