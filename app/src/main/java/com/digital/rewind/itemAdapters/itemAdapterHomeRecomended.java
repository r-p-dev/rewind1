package com.digital.rewind.itemAdapters;

import android.content.Context;
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
import com.digital.rewind.modals.modalHomeRecomendedSongs;
import com.digital.rewind.modals.modalSongs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class itemAdapterHomeRecomended extends RecyclerView.Adapter<itemAdapterHomeRecomended.ViewHolder> {
    List<modalHomeRecomendedSongs> wt_itemList;
    Context mContex;

    List<String>  namelist=new ArrayList<>();
    List<String> linklist=new ArrayList<>();


    public itemAdapterHomeRecomended(Context contex, List<modalHomeRecomendedSongs> wt_itemList) {
        this.wt_itemList = wt_itemList;
        mContex=contex;

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
        holder.recomended_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContex, "item clicked"+position, Toast.LENGTH_SHORT).show();
                for (int i=0;i<wt_itemList.size();i++){

                    namelist.add(wt_itemList.get(i).getSongName());
                    linklist.add(wt_itemList.get(i).getSongUrl());
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

        ((MainActivity)mContex).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
//                .hide(((MainActivity)mContext).activeFragment)
//                .show( fragment)
                .commit();
        ((MainActivity)mContex).bottomNavigation.show(3, true);
        ((MainActivity)mContex).activeFragment= ((MainActivity)mContex).playfragment;
        ((MainActivity)mContex).activeFragment= ((MainActivity)mContex).localfragment;

    }


    @Override
    public int getItemCount() {
        return wt_itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        com.google.android.material.imageview.ShapeableImageView imageitem;
        TextView textitem;
        RelativeLayout recomended_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageitem = itemView.findViewById(R.id.itemimg);
            textitem = itemView.findViewById(R.id.itemname);
            recomended_item=itemView.findViewById(R.id.recomended_song_item);
        }
    }
}
