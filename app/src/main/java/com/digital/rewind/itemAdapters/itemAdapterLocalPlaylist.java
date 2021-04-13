package com.digital.rewind.itemAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalLocalPlaylist;

import java.util.List;

public class itemAdapterLocalPlaylist extends RecyclerView.Adapter<itemAdapterLocalPlaylist.ViewHolder> {
    List<modalLocalPlaylist> localplaylistItemList;

    public itemAdapterLocalPlaylist(List<modalLocalPlaylist> localplaylistItemList) {
        this.localplaylistItemList = localplaylistItemList;

    }

    @NonNull
    @Override
    public itemAdapterLocalPlaylist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_playlists_local, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapterLocalPlaylist.ViewHolder holder, int position) {
        holder.localplaylistImage.setImageResource(localplaylistItemList.get(position).getLocalplaylistImage());
        holder.localplaylistNoOfSongs.setText(localplaylistItemList.get(position).getLocalnoOfSongsInPlaylist());
        holder.localplaylistName.setText(localplaylistItemList.get(position).getLocalnameOfPlaylist());

    }

    @Override
    public int getItemCount() {
        return localplaylistItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        com.google.android.material.imageview.ShapeableImageView localplaylistImage;
        TextView localplaylistName, localplaylistNoOfSongs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            localplaylistImage = itemView.findViewById(R.id.local_playlist_img);
            localplaylistNoOfSongs = itemView.findViewById(R.id.local_playlist_no_of_songs);
            localplaylistName = itemView.findViewById(R.id.local_playlist_name);
        }
    }
}
