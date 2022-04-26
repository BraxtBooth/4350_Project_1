package com.example.a4350_project_1;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {

    private ProfileFragment mProfileFragment;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Create the fragment
        mProfileFragment = new ProfileFragment();

        //Pass data to the fragment
        mProfileFragment.setArguments(getIntent().getExtras());

        userViewModel = new ViewModelProvider( this).get(UserViewModel.class);

        //No need to check if we're on a tablet. This activity only gets created on phones.
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.profile_activity_layout, mProfileFragment, "frag_profiledetail");
        fTrans.commit();
    }

    public void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        FloatingActionButton profilePicture = (FloatingActionButton) findViewById(R.id.fab);
        ImageView profilePicture = (ImageView) findViewById(R.id.ivProfileImage);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        saveToInternalStorage(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK  && data != null) {
                        try {
                            Uri imageUri = data.getData();
                            InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            saveToInternalStorage(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
//                            Toast.makeText(PostImage.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }else {
//                        Toast.makeText(PostImage.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    Log.println(Log.ERROR, "myTAG:", "Image upload didnt work.");
                    Log.println(Log.ERROR, "requestCode:", String.valueOf(requestCode));
                    Log.println(Log.ERROR, "data:", data.toString());
            }
        }
    }

    private String generateFilename(){
        return UUID.randomUUID().toString()+".JPG";
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String generatedFilename = generateFilename();
        File myPath = new File(directory, generatedFilename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // save new image URI to database
        userViewModel.updateUserImageURI(generatedFilename);

        return directory.getAbsolutePath();
    }


}
