package com.digital.rewind.itemAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalArtist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class itemAdapterHomePopularArtist extends RecyclerView.Adapter<itemAdapterHomePopularArtist.ViewHolder> {
    List<modalArtist> pa_itemList;
    Context mContex;

    public itemAdapterHomePopularArtist(Context contex,List<modalArtist> pa_itemList) {
        this.pa_itemList = pa_itemList;
        mContex=contex;

    }

    @NonNull
    @Override
    public itemAdapterHomePopularArtist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_home_popular_artist, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapterHomePopularArtist.ViewHolder holder, int position) {
        Picasso.get().load(pa_itemList.get(position).getArtistPic()).into(holder.pa_imageitem);

//        holder.pa_imageitem.setImageResource(pa_itemList.get(position).getImage());
        holder.pa_textitem.setText(pa_itemList.get(position).getArtistName());
    }

    @Override
    public int getItemCount() {
        return pa_itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pa_imageitem;
        TextView pa_textitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pa_imageitem = itemView.findViewById(R.id.popular_artist_img);
            pa_textitem = itemView.findViewById(R.id.popular_artist_name);
        }
    }
}
