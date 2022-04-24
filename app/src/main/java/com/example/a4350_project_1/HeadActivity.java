package com.example.a4350_project_1;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class HeadActivity extends AppCompatActivity
        implements MyRVAdapter.DataPasser{

    private static final CustomListData mCustomListData  = new CustomListData();
    private MasterListFragment mMasterListFragment;
    WeatherViewModel weatherViewModel;
    UserViewModel userViewModel;
//    private SensorManager mSensorManager;
//    private Sensor mYAccelerometer;
//    private Sensor mStepCounter;
//    private final double shakeSensitivityThreshold = 4.0;
//    private double last_y, now_y;
//    private boolean notFirstTime;
//    private MediaPlayer player;
//    int stepCount = 0;
//    private TextView tvSteps;
//    private boolean isCounterSensorPresent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) { // ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        tvSteps = (TextView) findViewById(R.id.tvStepCounter);

        //Put this into a bundle
//        Bundle fragmentBundle = new Bundle();
//        fragmentBundle.putParcelable("item_list", mCustomListData);

        //Create the fragment
        mMasterListFragment = new MasterListFragment();

        //Pass data to the fragment
//        mMasterListFragment.setArguments(fragmentBundle);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        userViewModel = new ViewModelProvider( this).get(UserViewModel.class);
        userViewModel.setCurrentUser();

//        // get sensor manager
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        // get default light(shake) sensor
//        mYAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
////        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
//            mStepCounter= mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
////            isCounterSensorPresent = true;
//        } else {
//            tvSteps.setText("--");
////            isCounterSensorPresent = false;
//        }

        //If we're on a tablet, the master fragment appears on the left pane. If we're on a phone,
        //it takes over the whole screen
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if(isTablet()){
            // replace dummy fragment in xml with mMasterListFragment
            fTrans.replace(R.id.masterlist_container_tablet, mMasterListFragment,"frag_masterlist");
        } else {
            fTrans.replace(R.id.masterlist_container_phone, mMasterListFragment, "frag_masterlist");
        }
        fTrans.commit();
    }

//    private SensorEventListener mAccelerometerListener = new SensorEventListener() {
//        @Override
//        public void onSensorChanged(SensorEvent sensorEvent) {
//            now_y = sensorEvent.values[1];
//            if(notFirstTime){
//                double dy = Math.abs(last_y - now_y);
//                if(dy > shakeSensitivityThreshold){
//                    Toast.makeText(getApplicationContext(), "it worked", Toast.LENGTH_SHORT).show();
//                    //Toast.makeText(this, "Step Counter STOPPED", Toast.LENGTH_SHORT).show();
//                    play();
//                    isCounterSensorPresent = true;
//                }
//            }
//            last_y = now_y;
//            notFirstTime = true;
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int i) {
//
//        }
//    };
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(mAccelerometerListener!=null){
//            mSensorManager.registerListener(mAccelerometerListener, mYAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//        }
//        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
//            mSensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(mYAccelerometer!=null){
//            mSensorManager.unregisterListener(mAccelerometerListener);
//        }
//        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
//            mSensorManager.unregisterListener(this, mStepCounter);
//    }

    //This receives the position of the clicked item in the MasterListFragment's RecyclerView
    @Override
    public void passData(int position) {
        //Get the string data corresponding to the detail view
        String itemDetailString = mCustomListData.getItemDetail(position);

        //Put this into a bundle
        Bundle detailBundle = new Bundle();
        detailBundle.putString("item_detail",itemDetailString);

        //If we're on a tablet, the fragment occupies the second pane (right). If we're on a phone,
        //the fragment is on a new Activity
        if(isTablet()) {
            if(position == 0){
                Fragment profileFragment = new ProfileFragment();
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.itemdetail_container_tablet, profileFragment, "profile-fragment");
                fTrans.commit();
            }
            else if(position == 1){
                Fragment bmiFragment = new BMIFragment();
                bmiFragment.setArguments(detailBundle);
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.itemdetail_container_tablet, bmiFragment, "bmi-fragment");
                fTrans.commit();
            }
            else if(position == 2){
                Fragment weatherFragment = new WeatherFragment();
                weatherFragment.setArguments(detailBundle);
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.itemdetail_container_tablet, weatherFragment, "weather-fragment");
                fTrans.commit();
            }
            else if(position == 3){
                String searchString = "Hikes near me";
                Uri searchUri = Uri.parse("geo:0,0?q=" + searchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
            else if(position == 4){
                String searchString = "Gym near me";
                Uri searchUri = Uri.parse("geo:0,0?q=" + searchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        }

        else{ //On a phone
            if(position == 0){
                Intent sendIntent = new Intent(this, ProfileActivity.class);
                sendIntent.putExtras(detailBundle);
                startActivity(sendIntent);
            }
            else if(position == 1){
                Intent sendIntent = new Intent(this, BMIActivity.class);
                sendIntent.putExtras(detailBundle);
                startActivity(sendIntent);
            }
            else if(position == 2){
                Intent sendIntent = new Intent(this, WeatherActivity.class);
                sendIntent.putExtras(detailBundle);
                startActivity(sendIntent);
            }
            else if(position == 3){
                String searchString = "Hikes near me";
                Uri searchUri = Uri.parse("geo:0,0?q=" + searchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
            else if(position == 4){
                String searchString = "Gym near me";
                Uri searchUri = Uri.parse("geo:0,0?q=" + searchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }

        }
    }

    boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }

    // = = = = = = = CODE FOR IMAGE UPLOAD

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

//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("image", generatedFilename);
//        editor.apply();

        userViewModel.updateUserImageURI(generatedFilename);

        return directory.getAbsolutePath();
    }
    // = = = = = = = END OF CODE FOR IMAGE UPLOAD

//    public void play(){
//        if(player == null){
//            player = MediaPlayer.create(this, R.raw.on);
//            Toast.makeText(this, "Step Counter ON", Toast.LENGTH_SHORT).show();
//            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    stopPlayer();
//                }
//            });
//        }
//        player.start();
//    }
//
//    public void stop(View v){
//        stopPlayer();
//    }
//
//    private void stopPlayer(){
//        if(player != null){
//            player.release();
//            player = null;
//        }
//    }
//
//    @Override protected void onStop() {
//        super.onStop();
//        stopPlayer();
//    }

//    @Override
//    public void onSensorChanged(SensorEvent sensorEvent) {
//        if(sensorEvent.sensor == mStepCounter && isCounterSensorPresent && sensorEvent.values[0] > 0){
//            stepCount = (int) sensorEvent.values[0];
//            tvSteps.setText(String.valueOf(stepCount));
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i) {
//
//    }
}
