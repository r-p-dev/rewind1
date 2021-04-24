package com.digital.rewind.itemAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.activitys.PlayerActivity;
import com.digital.rewind.modals.modalPlaylist;
import com.digital.rewind.modals.modalSongs;
import com.example.jean.jcplayer.model.JcAudio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class itemAdapterPlaylist extends RecyclerView.Adapter<itemAdapterPlaylist.ViewHolder> {
    List<modalPlaylist> playlistItemList;
    List<String>  namelist=new ArrayList<>();
    List<String> linklist=new ArrayList<>();
    Context mContext;
    List<modalSongs> song_itemList;

    public itemAdapterPlaylist(Context context, List<modalPlaylist> playlistItemList) {
        this.playlistItemList = playlistItemList;
        mContext=context;


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
            int count=playlistItemList.get(position).getSongs().size();
            String numb= String.valueOf(count);
            holder.playlistNoOfSongs.setText(numb);
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.println(playlistItemList.get(2).getSongs());
        holder.playlist_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("--------------------------------------------------------------------------");
                System.out.println("clicked");
                List<String> songkey;
                songkey=playlistItemList.get(position).getSongs();
                System.out.println("--------------------------------------------------------------------------");
                System.out.println(songkey);

                for (int i=0; i<songkey.size();i++){
                    System.out.println("playerrr----------------------------------------------------------------------");
                    System.out.println(songkey.get(i));
                    String s=songkey.get(i);
                    Query qery=FirebaseDatabase.getInstance().getReference("Songs/-"+s);
                    qery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            modalSongs data = snapshot.getValue(modalSongs.class);
                            System.out.println("playerrr--------------song detail--------------------------------------------------------");
                            System.out.println(data.getSongName());
                            System.out.println(data.getSongUrl());
                            String name=data.getSongName();
                            String link=data.getSongUrl();
                            namelist.add(name);
                            linklist.add(link);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                Intent intent=new Intent(mContext, PlayerActivity.class);
                intent.putExtra("linklist", (ArrayList<String>) linklist);
                intent.putExtra("namelist", (ArrayList<String>) namelist);
                intent.putExtra("position",0);
                mContext.startActivity(intent);


            }
        });

    }

    private void  song_initData(String key) {

    }

    @Override
    public int getItemCount() {
        return playlistItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        com.google.android.material.imageview.ShapeableImageView playlistImage;
        TextView playlistName, playlistNoOfSongs;
        ConstraintLayout playlist_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistImage = itemView.findViewById(R.id.playlist_image);
            playlistNoOfSongs = itemView.findViewById(R.id.playlist_num_songs);
            playlistName = itemView.findViewById(R.id.playlist_playlist_name);
            playlist_item=itemView.findViewById(R.id.playlist_item);
        }
    }
    private void findsongdata(String s) {

    }
}
