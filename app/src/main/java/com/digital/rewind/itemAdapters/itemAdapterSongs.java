package com.digital.rewind.itemAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.activitys.PlayerActivity;
import com.digital.rewind.modals.modalSongs;
import com.example.jean.jcplayer.model.JcAudio;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class itemAdapterSongs extends RecyclerView.Adapter<itemAdapterSongs.ViewHolder> {
    List<modalSongs> songs_itemList;
    private Context mContext;
    List<String>  namelist=new ArrayList<>();
    List<String> linklist=new ArrayList<>();
    public itemAdapterSongs(Context context,List<modalSongs> songs_itemList) {
        this.songs_itemList = songs_itemList;
        mContext = context;


    }

    @NonNull
    @Override
    public itemAdapterSongs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_songs, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapterSongs.ViewHolder holder, int position) {

        Picasso.get().load(songs_itemList.get(position).getImageUrl()).into(holder.songs_song_image);
//        holder.songs_song_image.setImageBitmap(getImage(songs_itemList.get(position).getImageUrl()));

        holder.songs_song_title.setText(songs_itemList.get(position).getSongName());
        holder.songs_song_art_name.setText(songs_itemList.get(position).getSongArtist());
        holder.songs_song_length.setText(songs_itemList.get(position).getSongDuration());
        holder.songitem_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "item clicked"+position, Toast.LENGTH_SHORT).show();
                for (int i=0;i<songs_itemList.size();i++){

                    namelist.add(songs_itemList.get(i).getSongName());
                    linklist.add(songs_itemList.get(i).getSongUrl());
                }
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("namelist", (ArrayList) namelist);
                intent.putExtra("linklist", (ArrayList) linklist);
                intent.putExtra("position", position);

                mContext.startActivity(intent);
            }
        });


    }

        public Bitmap getImage(String url) throws IOException {
            URL imageUrl = new URL(url);
            Bitmap bmp = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            return bmp;
    }

    @Override
    public int getItemCount() {
        return songs_itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        com.google.android.material.imageview.ShapeableImageView songs_song_image;
        TextView songs_song_title, songs_song_art_name, songs_song_length;
        ImageView songs_song_option;
        RelativeLayout songitem_item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songs_song_image = itemView.findViewById(R.id.songs_song_image);
            songs_song_title = itemView.findViewById(R.id.songs_song_title);
            songs_song_art_name = itemView.findViewById(R.id.songs_song_art_name);
            songs_song_length = itemView.findViewById(R.id.songs_song_length);
            songs_song_option = itemView.findViewById(R.id.songs_song_option_btn);
            songitem_item=itemView.findViewById(R.id.songitem_item);



            songs_song_option.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showOption(v);
        }

        private void showOption(View view) {
            PopupMenu optionMenu = new PopupMenu(view.getContext(), view);
            optionMenu.inflate(R.menu.option_menu);
            optionMenu.setOnMenuItemClickListener(this);
            optionMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.option_menu_play:
                    ArrayList<JcAudio> jcAudios = new ArrayList<>();

                    jcAudios.add(JcAudio.createFromURL(
                            songs_itemList.get(getAdapterPosition()).getSongName(),songs_itemList.get(getAdapterPosition()).getSongUrl()));
                    Toast.makeText(itemView.getContext(), "play option clicked @ in:" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.option_menu_playnext:
                    Toast.makeText(itemView.getContext(), "playNext option clicked @ in :" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.option_menu_addtoplaylist:
                    Toast.makeText(itemView.getContext(), "AddToPlayList option clicked @ in :" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        }
    }

}
