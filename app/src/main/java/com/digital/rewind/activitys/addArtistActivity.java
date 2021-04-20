package com.digital.rewind.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.digital.rewind.R;
import com.digital.rewind.modals.ArtistAdp;
import com.digital.rewind.modals.PlaylistAdp;
import com.digital.rewind.modals.SongAdp;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class addArtistActivity extends AppCompatActivity {
    ImageView image_for_new_artist;
    EditText name_for_new_artist;
    Button upload_btn_for_new_artist;
    ProgressDialog progressDialog;
    Uri aetistImageUri;
    byte[] imgbyte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artist);
        image_for_new_artist = findViewById(R.id.image_for_new_artist);
        name_for_new_artist = findViewById(R.id.name_for_new_artist);
        upload_btn_for_new_artist = findViewById(R.id.upload_new_artist);
       image_for_new_artist.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getImage();
           }
       });
       upload_btn_for_new_artist.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               uploadArtistImage();
           }
       });
    }



    private void getImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,3);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent imgg) {
        super.onActivityResult(requestCode, resultCode, imgg);
        if (imgg != null) {
            if (requestCode == 3 && resultCode == RESULT_OK){
              Log.i("image",imgg.toString());
                aetistImageUri = imgg.getData();
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(),aetistImageUri);
                    image_for_new_artist.setImageBitmap(bmp);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    imgbyte = byteArrayOutputStream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void uploadArtistImage() {
        if (aetistImageUri==null) {
            Toast.makeText(this, "Please select a Image Thumbnail", Toast.LENGTH_SHORT).show();
        }
        else if(name_for_new_artist.getText().toString().equals("") ){
            Toast.makeText(this, "name cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else {
            String namee=name_for_new_artist.getText().toString();
            pushImageToServer(aetistImageUri,namee);
        }
    }

    private void pushImageToServer(Uri imgbyte, String namee) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference ref = FirebaseStorage.getInstance().getReference("Artist/"+namee);
        ref.putFile(imgbyte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                while (!task.isComplete());
                Uri iu = task.getResult();
                String artistImageLink = iu.toString();
              Log.i("image url", artistImageLink);
                uploadDetailsToDatabase(namee,artistImageLink);
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("image url", "failed");
                progressDialog.dismiss();
            }
        });

    }

    public void uploadDetailsToDatabase(String name, String imageUrl){
        ArtistAdp arist = new ArtistAdp(name,imageUrl);
        FirebaseDatabase.getInstance().getReference("Artist")
                .push().setValue(arist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i("database", "upload success");

                Toast.makeText(getApplicationContext(), "Artist Created", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
