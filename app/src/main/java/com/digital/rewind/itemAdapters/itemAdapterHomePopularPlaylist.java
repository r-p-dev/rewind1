package com.digital.rewind.itemAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalHomePopularPlaylist;

import java.util.List;

public class itemAdapterHomePopularPlaylist extends RecyclerView.Adapter<itemAdapterHomePopularPlaylist.ViewHolder> {
    List<modalHomePopularPlaylist> pp_itemList;

    public itemAdapterHomePopularPlaylist(List<modalHomePopularPlaylist> pp_itemList) {
        this.pp_itemList = pp_itemList;

    }

    @NonNull
    @Override
    public itemAdapterHomePopularPlaylist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_home_popular_playlist, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapterHomePopularPlaylist.ViewHolder holder, int position) {
        holder.pp_imageitem.setImageResource(pp_itemList.get(position).getImage());
        holder.pp_textitem.setText(pp_itemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return pp_itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        com.google.android.material.imageview.ShapeableImageView pp_imageitem;
        TextView pp_textitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pp_imageitem = itemView.findViewById(R.id.popular_playlist_img);
            pp_textitem = itemView.findViewById(R.id.popular_playlist_name);
        }
    }
}
