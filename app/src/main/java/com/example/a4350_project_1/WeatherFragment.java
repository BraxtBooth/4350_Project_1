package com.example.a4350_project_1;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherFragment extends Fragment implements View.OnClickListener {

    private TextView tvWeatherSubtitle;
    private TextView tvTempVal;
    private TextView tvMaxTempVal;
    private TextView tvMinTempVal;
    private TextView tvHumidityVal;
    private TextView tvPressureVal;
    private TextView tvCloudsVal;
    private TextView tvSnowVal;
    private TextView tvRainVal;
    private WeatherData mWeatherData;
    private WeatherViewModel mWeatherViewModel;
    private UserViewModel userViewModel;
    String location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mFetchWeatherTask.setWeakReference(this);

        //Inflate the detail view
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        //Get the text view
//        mTvItemDetail = (TextView) view.findViewById(R.id.tvWeatherTitle);
        //Get the incoming detail text
        String detailString = getArguments().getString("item_detail");

//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        //Create the view model
        mWeatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        //Set the observer
        mWeatherViewModel.getWeatherData().observe(getViewLifecycleOwner(), nameObserver);
//        loadWeatherDataToScreen(sp.getString("location", ""));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserData().observe(getViewLifecycleOwner(), userObserver);

        location = userViewModel.getUserData().getValue().getLocation();
        loadWeatherDataToScreen(location);

        return view;
    }

    @Override
    public void onClick(View view) {

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvWeatherSubtitle = (TextView) getView().findViewById(R.id.tvWeatherSubtitle);
        tvTempVal = (TextView) getView().findViewById(R.id.tvTempValue);
        tvMaxTempVal = (TextView) getView().findViewById(R.id.tvMaxTempValue);
        tvMinTempVal = (TextView) getView().findViewById(R.id.tvMinTempValue);
        tvHumidityVal = (TextView) getView().findViewById(R.id.tvHumidityValue);
        tvPressureVal = (TextView) getView().findViewById(R.id.tvPressureValue);
        tvCloudsVal = (TextView) getView().findViewById(R.id.tvCloudValue);
        tvSnowVal = (TextView) getView().findViewById(R.id.tvSnowValue);
        tvRainVal = (TextView) getView().findViewById(R.id.tvRainValue);
    }

    // = = = Helper methods = = =
    public int kelvinToFahrenheit(double temp){
        return (int)(1.8 * (temp - 273.15) + 32);
    }

    public int roundToInt(double temp){
        return (int)Math.round(temp);
    }

    private void loadWeatherDataToScreen(String location){
        //pass the location in to the view model
        mWeatherViewModel.setLocation(location);
    }

    //create an observer that watches the LiveData<WeatherData> object
    final Observer<WeatherData> nameObserver  = new Observer<WeatherData>() {
        @Override
        public void onChanged(@Nullable final WeatherData weatherData) {
            // Update the UI if this data variable changes
            if(weatherData != null) {
                int temp = roundToInt(kelvinToFahrenheit(weatherData.getTemperature().getTemp()));
                int maxTemp = roundToInt(kelvinToFahrenheit(weatherData.getTemperature().getMaxTemp()));
                int minTemp = roundToInt(kelvinToFahrenheit(weatherData.getTemperature().getMinTemp()));
                int humidity = roundToInt(weatherData.getCurrentCondition().getHumidity());
                int pressure = roundToInt(weatherData.getCurrentCondition().getPressure());
                int cloudPercentage = roundToInt(weatherData.getClouds().getPerc());
                int snowAmount = roundToInt(weatherData.getSnow().getAmount());
                int rainAmount = roundToInt(weatherData.getRain().getAmount());

//                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
//                String location = sp.getString("location", "Austin,US");
//                tvWeatherSubtitle.setText("Weather information around " + location + ":");
                tvWeatherSubtitle.setText("Weather information around " + location + ":");
                tvTempVal.setText(temp + " F");
                tvMaxTempVal.setText(maxTemp + " F");
                tvMinTempVal.setText(minTemp + " F");
                tvHumidityVal.setText(humidity + "%");
                tvPressureVal.setText(pressure + " hPa");
                tvCloudsVal.setText(cloudPercentage + "%");
                tvSnowVal.setText(snowAmount + " mm");
                tvRainVal.setText(rainAmount + " mm");

            }
        }
    };

    final Observer<UserTable> userObserver = new Observer<UserTable>() {
        @Override
        public void onChanged(UserTable userTable) {
//            emailText.setText(userTable.getEmail());
//            nameText.setText(userTable.getName());
//            ageText.setText(String.valueOf(userTable.getAge()));
//            locationText.setText(userTable.getLocation());
//            heightFeet.setValue(userTable.getFeet());
//            heightInches.setValue(userTable.getInches());
//            weightText.setText(String.valueOf(userTable.getWeight()));
//            sexText.setSelection(userTable.getSex());
//            goalsText.setSelection(userTable.getGoal());
//            activityText.setSelection(userTable.getActivity());
//            lbsChange.setValue(userTable.getGoalChange());


//            location = userTable.getLocation();
//            loadWeatherDataToScreen(location);
//            tvWeatherSubtitle.setText("Weather information around " + location + ":");

        }
    };


}
