package com.digital.rewind.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.rewind.R;
import com.digital.rewind.fragments.HomeFragment;
import com.digital.rewind.fragments.LocalFragment;
import com.digital.rewind.fragments.PlayFragment;
import com.digital.rewind.fragments.PlayListFragment;
import com.digital.rewind.fragments.SongsFragment;
import com.digital.rewind.itemAdapters.myAdapter;
import com.digital.rewind.modals.model;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.jean.jcplayer.model.JcAudio;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "mainActivity";
    public  MeowBottomNavigation bottomNavigation;
    ImageView setting_btn;
    SearchView searchView;
    RecyclerView recview;
    myAdapter adapter;

    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    public Fragment homefragment = new HomeFragment();
    public Fragment localfragment = new LocalFragment();
    public  Fragment playfragment = new PlayFragment();
    public Fragment playlistfragment = new  PlayListFragment();
    public Fragment songsfragment = new SongsFragment();
    public Fragment activeFragment =homefragment;


    // TODO: Rename and change types of parame


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assigning buttom nav variable
        bottomNavigation = findViewById(R.id.bottom_navigation);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout,homefragment,"home")
                .add(R.id.frame_layout,localfragment,"home").hide(localfragment)
                .add(R.id.frame_layout,playfragment,"home").hide(playfragment)
                .add(R.id.frame_layout,playlistfragment,"home").hide(playlistfragment)
                .add(R.id.frame_layout,songsfragment,"home").hide(songsfragment)
                .commit();

        // adding menu items
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_music));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_play));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_playlistplay));
        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_local));
        setting_btn = findViewById(R.id.setting_btn);
        searchView=findViewById(R.id.searchbtn);
        recview=findViewById(R.id.recview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recview.setAdapter(null);
                return false;
            }
        });
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
                        fragment = homefragment;
                        break;
                    case 2:
                        fragment = songsfragment;
                        break;
                    case 3:
                        fragment = playfragment;
                        break;
                    case 4:
                        fragment = playlistfragment;
                        break;
                    case 5:
                        fragment = localfragment;
                        break;

                }
                //loading fragment
                loadFragment(fragment);
            }
        });

        bottomNavigation.show(1, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
//                Fragment fragment = null;
//
//                switch (item.getId()) {
//                    case 1:
//                        fragment = homefragment;
//                        break;
//                    case 2:
//                        fragment = songsfragment;
//                        break;
//                    case 3:
//                        fragment = playfragment;
//                        break;
//                    case 4:
//                        fragment = playlistfragment;
//                        break;
//                    case 5:
//                        fragment = localfragment;
//                        break;
//
//                }
//                //loading fragment
//                loadFragment(fragment);
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });




    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Songs").orderByChild("songName").startAt(s).endAt(s+"\uf8ff"), model.class)
                        .build();

        adapter=new myAdapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }


    private void loadFragment(Fragment fragment) {
        //replacing fragment
//        if (activeFragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(activeFragment)
                    .show(fragment)
                    .commit();
            activeFragment=fragment;
//        }else{
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.frame_layout,fragment)
//                    .commit();
//            activeFragment=fragment;
//        }
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
            case R.id.add_new_artist:
                Intent iv = new Intent(MainActivity.this, addArtistActivity.class);
                startActivity(iv);
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


























