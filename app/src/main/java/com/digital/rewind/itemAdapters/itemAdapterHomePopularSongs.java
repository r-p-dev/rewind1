package com.digital.rewind.itemAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.activitys.MainActivity;
import com.digital.rewind.fragments.PlayFragment;
import com.digital.rewind.modals.modalSongs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class itemAdapterHomePopularSongs extends RecyclerView.Adapter<itemAdapterHomePopularSongs.ViewHolder> {
    List<modalSongs> ps_itemList;
Context mContext;
    List<String>  namelist=new ArrayList<>();
    List<String> linklist=new ArrayList<>();
    public itemAdapterHomePopularSongs(Context context,List<modalSongs> ps_itemList) {
        this.ps_itemList = ps_itemList;
mContext=context;
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
        Picasso.get().load(ps_itemList.get(position).getImageUrl()).into(holder.ps_imageitem);
//
//        holder.ps_imageitem.setImageResource(R.drawable.headphone);
        holder.ps_textitem.setText(ps_itemList.get(position).getSongName());
        holder.pscontainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "item clicked"+position, Toast.LENGTH_SHORT).show();
                for (int i=0;i<ps_itemList.size();i++){

                    namelist.add(ps_itemList.get(i).getSongName());
                    linklist.add(ps_itemList.get(i).getSongUrl());
                }
                Fragment pf=new PlayFragment();
                Bundle b=new Bundle();
                b.putStringArrayList("namelist", (ArrayList<String>) namelist);
                b.putStringArrayList("linklist", (ArrayList<String>) linklist);
                b.putInt("position",position);
                pf.setArguments(b);
                loadFragment(pf);
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        //replacing fragment

        ((MainActivity)mContext).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
//                .hide(((MainActivity)mContext).activeFragment)
//                .show( fragment)
                .commit();
        ((MainActivity)mContext).bottomNavigation.show(3, true);
        ((MainActivity)mContext).activeFragment= ((MainActivity)mContext).playfragment;
        ((MainActivity)mContext).activeFragment= ((MainActivity)mContext).localfragment;




    }


    @Override
    public int getItemCount() {
        return ps_itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        com.google.android.material.imageview.ShapeableImageView ps_imageitem;
        TextView ps_textitem;
        RelativeLayout  pscontainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ps_imageitem = itemView.findViewById(R.id.popular_songs_img);
            ps_textitem = itemView.findViewById(R.id.popular_songs_name);
            pscontainer= itemView.findViewById(R.id.pscontainer);
        }
    }
}
