package com.example.a4350_project_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a4350_project_1.databinding.FragmentFirstBinding;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private FetchWeatherTask mFetchWeatherTask = new FetchWeatherTask();
    private WeatherData mWeatherData;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFetchWeatherTask.setWeakReference(this);
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);

//        SharedPreferences.Editor preferencesEditor = sp.edit();
//        preferencesEditor.putInt("weight", 24);
//        preferencesEditor.putInt("age", 42);
//        preferencesEditor.apply();

        TextView BMRText = (TextView) getView().findViewById(R.id.textViewBMRValue);
        TextView CaloriesText = (TextView) getView().findViewById(R.id.textViewCaloriesValue);
        TextView CaloriesWarning = (TextView) getView().findViewById(R.id.textViewCalorieWarning);
        String BMRTextOutput = "Please enter height, weight, age, and sex on profile page";
        String CaloriesTextOutput = "";
        float feet = sp.getInt("feet", 0);
        float inches = sp.getInt("inches", 0);
        float weight = sp.getInt( "weight", 0);
        float age = sp.getInt("age", 0);
        int sex = sp.getInt("sex", 0);
        String[] sexEntries = getResources().getStringArray(R.array.Sex_Array);
        int activityIndex = sp.getInt("activity", 0);
        int goalIndex = sp.getInt("goals", 0);
        Integer lbsChange = sp.getInt("lbschange", 0);
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

        if((calorieIntake < 1200 && sexEntries[sex].equals("Male")) || (calorieIntake<1000 && sexEntries[sex].equals("Females"))){
            CaloriesWarning.setVisibility(View.VISIBLE);
        }
        else{
            CaloriesWarning.setVisibility(View.GONE);
        }

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.buttonHikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchString = "Hikes near me";
                Uri searchUri = Uri.parse("geo:0,0?q=" + searchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        binding.buttonBMI.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
                String outputText = "Please enter height and weight on profile page";

                float feet = sp.getInt("feet", 0);
                float inches = sp.getInt("inches", 0);
                float weight = sp.getInt( "weight", 0);

                if(feet !=0 && weight != 0){
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    float totalHeight = feet*12 + inches;
                    float bmi = (weight/(totalHeight*totalHeight))*703;
                    outputText = "BMI: " + String.valueOf(df.format(bmi));
                }
                Activity activity = getActivity();
                Toast.makeText(activity, outputText, Toast.LENGTH_LONG).show();
            }
        });

        binding.buttonWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWeatherData(sp.getString("location", ""));
            }
        });


    }

    private void loadWeatherData(String location){
        mFetchWeatherTask.execute(this,location);
    }
    private class FetchWeatherTask{
        WeakReference<FirstFragment> weatherFragmentWeakReference;
        private ExecutorService executorService = Executors.newSingleThreadExecutor();
        private Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        public void setWeakReference(FirstFragment ref)
        {
            weatherFragmentWeakReference = new WeakReference<FirstFragment>(ref);
        }
        public void execute(FirstFragment ref, String location){

            executorService.execute(new Runnable(){
                @Override
                public void run(){
                    String jsonWeatherData;
                    URL weatherDataURL = NetworkUtils.buildURLFromString(location);
                    jsonWeatherData = null;
                    try{
                        jsonWeatherData = NetworkUtils.getDataFromURL(weatherDataURL);
                        postToMainThread(jsonWeatherData);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

        private void postToMainThread(String jsonWeatherData)
        {
            FirstFragment localRef = weatherFragmentWeakReference.get();
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (jsonWeatherData != null) {
                        try {
                            localRef.mWeatherData = JSONWeatherUtils.getWeatherData(jsonWeatherData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (localRef.mWeatherData != null) {
//                            localRef.mTvTemp.setText("" + Math.round(localRef.mWeatherData.getTemperature().getTemp() - 273.15) + " C");
//                            localRef.mTvHum.setText("" + localRef.mWeatherData.getCurrentCondition().getHumidity() + "%");
//                            localRef.mTvPress.setText("" + localRef.mWeatherData.getCurrentCondition().getPressure() + " hPa");
                            int fahrenheit_val = (int)(Math.round(1.8*(localRef.mWeatherData.getTemperature().getTemp() - 273.15) + 32));
                            Toast.makeText(getActivity(),  String.valueOf(fahrenheit_val) + " F", Toast.LENGTH_LONG).show();
//                            Toast.makeText(activity, outputText, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}