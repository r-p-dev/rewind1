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

import com.digital.rewind.R;
import com.digital.rewind.fragments.HomeFragment;
import com.digital.rewind.fragments.LocalFragment;
import com.digital.rewind.fragments.PlayFragment;
import com.digital.rewind.fragments.PlayListFragment;
import com.digital.rewind.fragments.SongsFragment;
import com.digital.rewind.modals.modalLocalSongs;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private static final String TAG = "mainActivity";
    MeowBottomNavigation bottomNavigation;
    ImageView setting_btn;
    FloatingActionButton fab;
    Uri uri;
    String songName, songUrl,songArtist, songLength;
    boolean playStatus = true;
    byte[] songArt;
    MediaMetadataRetriever metaRetriver;
    private FirebaseAuth mAuth;
    private boolean checkPermission = false;


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
                uploadSong();
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

    private void uploadSong() {
        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (validatePermision()){
                    uri = data.getData();
                    String[] mediaProjection = {
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.DURATION
                    };
                    android.database.Cursor mcursor = getApplicationContext().getContentResolver()
                            .query(uri, mediaProjection, null, null, null);
                    try {
                        mcursor.moveToFirst();


                        songName = mcursor.getString(mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                        String artdata=mcursor.getString(mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                        songArtist =mcursor.getString(mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                        songLength=mcursor.getString(mcursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                      uploadSongToFirebaseStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mcursor.close();
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean validatePermision() {

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        checkPermission = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        checkPermission = false;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        return checkPermission;
    }


    private void uploadSongToFirebaseStorage() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("Songs").child(uri.getLastPathSegment());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlSong = uriTask.getResult();
                songUrl = urlSong.toString();

                uploadDetailsToDatabase();
                progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progres = (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                int currentProgress = (int)progres;
                progressDialog.setMessage("Uploaded: "+currentProgress+"%");
            }
        });




    }

    private void uploadDetailsToDatabase() {

        song songObj = new song(songName,songUrl,songArtist,songLength);

        FirebaseDatabase.getInstance().getReference("Songs")
                .push().setValue(songObj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Song Uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

//
//    private String getAlbumArtist(String uri) {
//        String artist = null;
//
//        if (validatePermision()){
//            MediaMetadataRetriever retriver = new MediaMetadataRetriever();
//            retriver.setDataSource(uri);
//             artist = retriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
//            retriver.release();
//            if (artist==null){
//                artist="Unknown Artist";
//            }
//            return artist;
//        }
//
//        return artist;
//    }
//    private String getSongDuration(String uri) {
//        MediaMetadataRetriever retriver = new MediaMetadataRetriever();
//        retriver.setDataSource(uri);
//        String duration = retriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//        retriver.release();
//        if (duration==null){
//            duration="00:00";
//        }
//        return duration;
//    }


}


























