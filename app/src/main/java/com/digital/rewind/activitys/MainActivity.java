package com.digital.rewind.activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.fragments.HomeFragment;
import com.digital.rewind.fragments.LocalFragment;
import com.digital.rewind.fragments.PlayFragment;
import com.digital.rewind.fragments.PlayListFragment;
import com.digital.rewind.fragments.SongsFragment;
import com.digital.rewind.itemAdapters.itemAdapterHomePopularPlaylist;
import com.digital.rewind.itemAdapters.itemAdapterHomePopularSongs;
import com.digital.rewind.itemAdapters.itemAdapterHomeRecomended;
import com.digital.rewind.modals.modalHomePopularArtist;
import com.digital.rewind.modals.modalLocalSongs;
import com.digital.rewind.modals.modalPlaylist;
import com.digital.rewind.modals.modalSongs;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "mainActivity";
    MeowBottomNavigation bottomNavigation;
    ImageView setting_btn;
    FloatingActionButton fab;
    boolean playStatus = true;
    // TODO: Rename and change types of parame


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assigning buttom nav variable
        bottomNavigation = findViewById(R.id.bottom_navigation);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        // adding menu items
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_music));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_play));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_playlistplay));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_local));
        setting_btn = findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOption(v);
            }

            private void showOption(View view) {
                PopupMenu settingMenu = new PopupMenu(MainActivity.this, view);
                settingMenu.inflate(R.menu.setting_menu);
                settingMenu.setOnMenuItemClickListener(MainActivity.this);
                settingMenu.show();
            }

        });


        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //initilizaing fragment
                Fragment fragment = null;

                //checking condition

                switch (item.getId()) {
                    case 1:
                        fragment = new HomeFragment();
                        break;
                    case 2:
                        fragment = new SongsFragment();
                        break;
                    case 3:
                        fragment = new PlayFragment();
                        break;
                    case 4:
                        fragment = new PlayListFragment();
                        break;
                    case 5:
                        fragment = new LocalFragment();
                        break;

                }
                //loading fragment
                loadFragment(fragment);
            }
        });
        //setting home selected
        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
    }


    private void loadFragment(Fragment fragment) {
        //replacing fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        FloatingActionButton fav = findViewById(R.id.fab);
        if (playStatus == false) {
            fav.setImageResource(R.drawable.ic_play);
            playStatus = true;
        } else if (playStatus == true) {
            fav.setImageResource(R.drawable.ic_pause);
            playStatus = false;
        }


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upload:
//                uploadSong();
                Intent ii = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(ii);
                finish();
                return true;
            case R.id.new_playlist:
//                uploadSong();
                Intent iii = new Intent(MainActivity.this, NewPlaylistActivity.class);
                startActivity(iii);
                finish();
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out!!", Toast.LENGTH_SHORT);
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(MainActivity.this, "Logged Out!!", Toast.LENGTH_SHORT);
                return true;

            default:
                return false;
        }
    }




}


























