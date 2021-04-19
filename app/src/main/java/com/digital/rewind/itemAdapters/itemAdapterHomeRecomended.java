package com.digital.rewind.itemAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalHomeRecomendedSongs;
import com.digital.rewind.modals.modalSongs;
import com.squareup.picasso.Picasso;

import java.util.List;

public class itemAdapterHomeRecomended extends RecyclerView.Adapter<itemAdapterHomeRecomended.ViewHolder> {
    List<modalHomeRecomendedSongs> wt_itemList;

    public itemAdapterHomeRecomended(List<modalHomeRecomendedSongs> wt_itemList) {
        this.wt_itemList = wt_itemList;

    }

    @NonNull
    @Override
    public itemAdapterHomeRecomended.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_home_recomended, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapterHomeRecomended.ViewHolder holder, int position) {
        Picasso.get().load(wt_itemList.get(position).getImageUrl()).into(holder.imageitem);
        holder.textitem.setText(wt_itemList.get(position).getSongName());
    }

    @Override
    public int getItemCount() {
        return wt_itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        com.google.android.material.imageview.ShapeableImageView imageitem;
        TextView textitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageitem = itemView.findViewById(R.id.itemimg);
            textitem = itemView.findViewById(R.id.itemname);
        }
    }
}
