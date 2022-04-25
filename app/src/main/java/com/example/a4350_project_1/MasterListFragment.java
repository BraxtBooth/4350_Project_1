package com.example.a4350_project_1;

import android.content.Context;

import androidx.fragment.app.Fragment;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MasterListFragment extends Fragment implements SensorEventListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private UserViewModel userViewModel;
    TextView BMRText;
    TextView CaloriesText;
    TextView CaloriesWarning;
    float feet;
    float inches;
    float weight;
    float age;
    int sex;
    String[] sexEntries ;
    int activityIndex;
    int goalIndex;
    Integer lbsChange;

    private SensorManager mSensorManager;
    private Sensor mYAccelerometer;
    private Sensor mStepCounter;
    private final double shakeSensitivityThresholdY = 20.0;
    private final double shakeSensitivityThresholdX = 15.0;
    private double last_y, now_y, last_x, now_x;
    private boolean notFirstTimeY;
    private boolean notFirstTimeX;
    private MediaPlayer player;
    int stepCount = 0;
    private TextView tvSteps;
    private boolean isCounterSensorPresent;
    private boolean stepCounterOn;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_master_list, container, false);

        stepCounterOn = false;

        //Get the recycler view
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.rv_Master);

        //Tell Android that we know the size of the recyclerview doesn't change
        mRecyclerView.setHasFixedSize(true);

        //Set the layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        //Get data from main activity
        CustomListData customListData = new CustomListData();
        List<String> inputList = customListData.getItemList();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserData().observe(getViewLifecycleOwner(), userObserver);

        //Set the adapter
        mAdapter = new MyRVAdapter(inputList);
        mRecyclerView.setAdapter(mAdapter);

        // get sensor manager
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        // get default light(shake) sensor
        mYAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
//        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            mStepCounter= mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//            isCounterSensorPresent = true;
        } else {
//            tvSteps.setText("--");
//            isCounterSensorPresent = false;
        }

        setRetainInstance(true);

        return fragmentView;
    }


    private SensorEventListener mAccelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            now_x = sensorEvent.values[0];
            now_y = sensorEvent.values[1];
            if(notFirstTimeY){
                double dy = Math.abs(last_y - now_y);
                if(dy > shakeSensitivityThresholdY){
                    if(!stepCounterOn){
                        stepCounterOn = true;
                        Toast.makeText(getContext(), "Step Counter ON", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(this, "Step Counter STOPPED", Toast.LENGTH_SHORT).show();
                        play(true);
                        isCounterSensorPresent = true;
                    }
                }
            }
            if(notFirstTimeX){
                double dx = Math.abs(last_x - now_x);
                if(dx > shakeSensitivityThresholdX){
                    if(stepCounterOn){
                        stepCounterOn = false;
                        Toast.makeText(getContext(), "Step Counter OFF", Toast.LENGTH_SHORT).show();
                        play(false);
                        isCounterSensorPresent = false;
                        mSensorManager.unregisterListener(this, mStepCounter);
                        tvSteps.setText("Not Activated");

                        //public LiveData<UserTable> getUserData(){
                        //        return userData;
                        //    }

//                        // testing date formats
//                        Calendar calendar;
//                        SimpleDateFormat dateFormat;
//                        String date;
//                        calendar = Calendar.getInstance();
//                        dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
//                        date = dateFormat.format(calendar.getTime());
//                        Toast.makeText(this, date, Toast.LENGTH_LONG).show();
                        String steps = userViewModel.getUserData().getValue().getSteps();
                        String dates = userViewModel.getUserData().getValue().getDates();
//                        String[] stepsArray = steps.split("/");
//                        String[] datesArray = steps.split("/");
                        ArrayList<String> stepsArray = new ArrayList<>(Arrays.asList(steps.split("/")));  //  23/5454/23232/43423/
                        ArrayList<String> datesArray = new ArrayList<>(Arrays.asList(dates.split("/")));
                        if(stepsArray.size() == 10) stepsArray.remove(stepsArray.size()-1);
                        if(datesArray.size() == 10) datesArray.remove(datesArray.size()-1);

                        // setting the date
                        Calendar calendar;
                        SimpleDateFormat dateFormat;
                        String date;
                        calendar = Calendar.getInstance();
                        dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        date = dateFormat.format(calendar.getTime());
                        stepsArray.add(0, String.valueOf(stepCount));
                        datesArray.add(0, date);
                        userViewModel.updateUserSteps(String.join("/", stepsArray));
                        userViewModel.updateUserDates(String.join("/", datesArray));

                        stepCount = 0;
                    }
                }
            }
            last_y = now_y;
            notFirstTimeY = true;
            last_x = now_x;
            notFirstTimeX = true;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    @Override
    public void onResume() {
        super.onResume();
        if(mAccelerometerListener!=null){
            mSensorManager.registerListener(mAccelerometerListener, mYAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            mSensorManager.registerListener(this, mStepCounter, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mYAccelerometer!=null){
            mSensorManager.unregisterListener(mAccelerometerListener);
        }
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            mSensorManager.unregisterListener(this, mStepCounter);
    }

    final Observer<UserTable> userObserver = new Observer<UserTable>() {
        @Override
        public void onChanged(UserTable userTable) {
            feet = userTable.getFeet();
            inches = userTable.getInches();
            weight = userTable.getWeight();
            age = userTable.getAge();
            sex = userTable.getSex();
            sexEntries = getResources().getStringArray(R.array.Sex_Array);
            activityIndex = userTable.getActivity();
            goalIndex = userTable.getGoal();
            lbsChange = userTable.getGoalChange();
            String BMRTextOutput = "Please enter height, weight, age, and sex on profile page";
            String CaloriesTextOutput = "";
            float bmr = 0;
            float calorieFactor = (float) (activityIndex==0 ? 1.55 : 1.2);
            float calorieIntake = 0;
            if(feet !=0 && weight!=0 && age != 0){
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                float totalHeight = feet*12 + inches;
                if(sexEntries[sex].equals("Male")){
                    bmr = (float) (66.47+(6.24*weight)+(12.7*totalHeight)-(6.755*age));
                    BMRTextOutput = String.valueOf(df.format(bmr));
                }
                else if(sexEntries[sex].equals("Female")){
                    bmr = (float) (655.1 + (4.35*weight) + (4.7*totalHeight) - (4.7*age));
                    BMRTextOutput = String.valueOf(df.format(bmr));
                }
                else{
                    BMRTextOutput = "Cannot calculate BMR based on Sex";
                }

                if(bmr != 0) {
                    if (goalIndex == 0) {
                        //Gain Weight
                        calorieIntake = bmr*calorieFactor+(lbsChange==1 ? 500: 1000);
                        CaloriesTextOutput = String.valueOf(df.format(calorieIntake));

                    } else if (goalIndex == 1) {
                        //Lose Weight
                        calorieIntake = bmr*calorieFactor-(lbsChange==1 ? 500: 1000);
                        CaloriesTextOutput = String.valueOf(df.format(calorieIntake));
                    } else if (goalIndex == 2) {
                        //Maintain Weight
                        calorieIntake = bmr*calorieFactor;
                        CaloriesTextOutput = String.valueOf(df.format(calorieIntake));
                    }
                }
            }
            BMRText.setText(BMRTextOutput);
            CaloriesText.setText(CaloriesTextOutput);

            if((calorieIntake < 1200 && sexEntries[sex].equals("Male")) || (calorieIntake<1000 && sexEntries[sex].equals("Females")))
                CaloriesWarning.setVisibility(View.VISIBLE);
            else
                CaloriesWarning.setVisibility(View.GONE);

            ImageView profileImg = (ImageView) getView().findViewById(R.id.ivProfileImage);
            if(userTable.getImageURI() == ""){
                profileImg.setImageResource(R.drawable.user_icon);
            }else{
                loadImageFromStorage(userTable.getImageURI());
            }
        }
    };

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getContext());
//        String imageUri = sp.getString("image", "");
//        Log.println(Log.ASSERT, "===============myTAG:", imageUri);

//        ImageView profileImg = (ImageView) getView().findViewById(R.id.ivProfileImage);
//        if(imageUri == ""){
//            profileImg.setImageResource(R.drawable.user_icon);
//        }else{
//            loadImageFromStorage(imageUri);
//        }

        BMRText = (TextView) getView().findViewById(R.id.tvMBRValue);
        CaloriesText = (TextView) getView().findViewById(R.id.tvCaloriesValue);
        CaloriesWarning = (TextView) getView().findViewById(R.id.tvCalorieWarning);

        tvSteps = (TextView) getView().findViewById(R.id.tvStepCounter);

//        String BMRTextOutput = "Please enter height, weight, age, and sex on profile page";
//        String CaloriesTextOutput = "";
//        float feet = sp.getInt("feet", 0);
//        float inches = sp.getInt("inches", 0);
//        float weight = sp.getInt( "weight", 0);
//        float age = sp.getInt("age", 0);
//        int sex = sp.getInt("sex", 0);
//        String[] sexEntries = getResources().getStringArray(R.array.Sex_Array);
//        int activityIndex = sp.getInt("activity", 0);
//        int goalIndex = sp.getInt("goals", 0);
//        Integer lbsChange = sp.getInt("lbschange", 0);
//        float bmr = 0;
//        float calorieFactor = (float) (activityIndex==0 ? 1.55 : 1.2);
//        float calorieIntake = 0;
//
//        if(feet !=0 && weight!=0 && age != 0){
//            DecimalFormat df = new DecimalFormat();
//            df.setMaximumFractionDigits(2);
//            float totalHeight = feet*12 + inches;
//            if(sexEntries[sex].equals("Male")){
//                bmr = (float) (66.47+(6.24*weight)+(12.7*totalHeight)-(6.755*age));
//                BMRTextOutput = String.valueOf(df.format(bmr));
//            }
//            else if(sexEntries[sex].equals("Female")){
//                bmr = (float) (655.1 + (4.35*weight) + (4.7*totalHeight) - (4.7*age));
//                BMRTextOutput = String.valueOf(df.format(bmr));
//            }
//            else{
//                BMRTextOutput = "Cannot calculate BMR based on Sex";
//            }
//
//            if(bmr != 0) {
//                if (goalIndex == 0) {
//                    //Gain Weight
//                    calorieIntake = bmr*calorieFactor+(lbsChange==1 ? 500: 1000);
//                    CaloriesTextOutput = String.valueOf(df.format(calorieIntake));
//
//                } else if (goalIndex == 1) {
//                    //Lose Weight
//                    calorieIntake = bmr*calorieFactor-(lbsChange==1 ? 500: 1000);
//                    CaloriesTextOutput = String.valueOf(df.format(calorieIntake));
//                } else if (goalIndex == 2) {
//                    //Maintain Weight
//                    calorieIntake = bmr*calorieFactor;
//                    CaloriesTextOutput = String.valueOf(df.format(calorieIntake));
//                }
//            }
//        }
//        BMRText.setText(BMRTextOutput);
//        CaloriesText.setText(CaloriesTextOutput);
//
//        if((calorieIntake < 1200 && sexEntries[sex].equals("Male")) || (calorieIntake<1000 && sexEntries[sex].equals("Females"))){
//            CaloriesWarning.setVisibility(View.VISIBLE);
//        }
//        else{
//            CaloriesWarning.setVisibility(View.GONE);
//        }

    }

    private void loadImageFromStorage(String imgName)
    {
        ContextWrapper cw = new ContextWrapper(getContext());
        File directoryPath = cw.getDir("imageDir", Context.MODE_PRIVATE);

        try {
            File f = new File(directoryPath, imgName);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = getView().findViewById(R.id.ivProfileImage);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void play(boolean stepCounterTurnedOn){
        if(player == null){
            if(stepCounterTurnedOn){
                player = MediaPlayer.create(getContext(), R.raw.on);
//            Toast.makeText(getContext(), "Step Cthisounter ON", Toast.LENGTH_SHORT).show();
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        stopPlayer();
                    }
                });
            } else {
                player = MediaPlayer.create(getContext(), R.raw.off);
//            Toast.makeText(getContext(), "Step Cthisounter ON", Toast.LENGTH_SHORT).show();
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        stopPlayer();
                    }
                });
            }

        }
        player.start();
    }

    public void stop(View v){
        stopPlayer();
    }

    private void stopPlayer(){
        if(player != null){
            player.release();
            player = null;
        }
    }

    @Override public void onStop() {
        super.onStop();
        stopPlayer();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == mStepCounter && isCounterSensorPresent && sensorEvent.values[0] > 0){
//            stepCount = (int) sensorEvent.values[0];
            stepCount++;
            tvSteps.setText(String.valueOf(stepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
