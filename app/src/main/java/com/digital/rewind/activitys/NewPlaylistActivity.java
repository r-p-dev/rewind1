package com.digital.rewind.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.digital.rewind.R;
import com.digital.rewind.modals.modalPlaylist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewPlaylistActivity extends AppCompatActivity {
    ImageView playlistImage;
    EditText playlistName, playlistCreator;
    Button createPlaylistButton, insert;
    ProgressDialog progressDialog;
    Uri uriImage;
    byte[] bytes;
    String filename, imageUrl;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_playlist);
        playlistImage = findViewById(R.id.newPlaylistimage);
        playlistName = findViewById(R.id.newPlaylistName);
        playlistCreator = findViewById(R.id.newPlaylistCreatorName);
        createPlaylistButton = findViewById(R.id.uploadPlaylistButton);
        progressDialog = new ProgressDialog(this);
//        insert = findViewById(R.id.insertPlaylist);
//        insert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> ar =new ArrayList<String>();
//                ar.add("MYPUKjQTsuI1oAaTLcv");
//                ar.add("MYP6KME4MvQJPi2u1JY");
//                PlaylistAdp il = new PlaylistAdp("https://firebasestorage.googleapis.com/v0/b/musicrewind-16e8f.appspot.com/o/Thumbnails%2F3idiots01%20(2).mp3?alt=media&token=c3810d54-c670-4124-af15-7624d8dc7a52", "Test play list try try", "test creator", ar);
//                FirebaseDatabase.getInstance().getReference("Playlist/Shared")
//                        .push().setValue(il).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(NewPlaylistActivity.this, "Playlist created", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        storageReference = FirebaseStorage.getInstance().getReference();

        playlistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }

    private void  pickImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,3);
    }

//         AFTER SELECTING THE SONG FROM MOBILE STORAGE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 3 && resultCode == RESULT_OK){
//                Log.i("image",data.toString());
                uriImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uriImage);
                    playlistImage.setImageBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    bytes = byteArrayOutputStream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void uploadPlaylist(View view){
        if (uriImage == null){
            Toast.makeText(this, "Please select a Image Thumbnail", Toast.LENGTH_SHORT).show();
        }
        else if (playlistName.getText().toString().equals("")){
            Toast.makeText(this, "playlist name cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else if(playlistCreator.getText().toString().equals("")){
            Toast.makeText(this, "Please add Creator name", Toast.LENGTH_SHORT).show();
        }
        else {
            List<String> ar =new ArrayList<String>();
           String name = playlistName.getText().toString();
            String creator = playlistCreator.getText().toString();
            uploadImageToServer(bytes,name,creator);
        }
    }

    public void uploadImageToServer(byte[] image, String name,String creator) {
        UploadTask uploadTask = storageReference.child("PlaylistArt").child(name).putBytes(image);
        progressDialog.show();
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                while (!task.isComplete());
                Uri urlimg = task.getResult();
               String imageUrl = urlimg.toString();
//                Log.i("image url", imageUrl);
                uploadDetailsToDatabase(name,imageUrl,creator);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("image url", "failed");
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override

            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            progressDialog.setMessage("uploading image");
            }
        });
    }

    public void uploadDetailsToDatabase(String name, String imageUrl, String creator){
        modalPlaylist plist = new modalPlaylist(imageUrl,name,creator);
        progressDialog.setMessage("updating to database");
        FirebaseDatabase.getInstance().getReference("Playlist/Shared")
                .push().setValue(plist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("database", "upload success");

                Toast.makeText(getApplicationContext(), "playlist Created", Toast.LENGTH_SHORT).show();

                finish();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

}
