package com.digital.rewind.itemAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalHomePopularSongs;

import java.util.List;

public class itemAdapterHomePopularSongs extends RecyclerView.Adapter<itemAdapterHomePopularSongs.ViewHolder> {
    List<modalHomePopularSongs> ps_itemList;

    public itemAdapterHomePopularSongs(List<modalHomePopularSongs> ps_itemList) {
        this.ps_itemList = ps_itemList;

    }

    @NonNull
    @Override
    public itemAdapterHomePopularSongs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_home_popular_songs, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapterHomePopularSongs.ViewHolder holder, int position) {
        holder.ps_imageitem.setImageResource(ps_itemList.get(position).getImage());
        holder.ps_textitem.setText(ps_itemList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return ps_itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        com.google.android.material.imageview.ShapeableImageView ps_imageitem;
        TextView ps_textitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ps_imageitem = itemView.findViewById(R.id.popular_songs_img);
            ps_textitem = itemView.findViewById(R.id.popular_songs_name);
        }
    }
}
