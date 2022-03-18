package com.example.a4350_project_1;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class WeatherActivity extends AppCompatActivity {

    private WeatherFragment weatherFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);

        //Create the fragment
        weatherFragment = new WeatherFragment();

        //Pass data to the fragment
        weatherFragment.setArguments(getIntent().getExtras());

        //No need to check if we're on a tablet. This activity only gets created on phones.
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.bmi_activity_layout, weatherFragment, "weather_fragment_layout");
        fTrans.commit();
    }
}
