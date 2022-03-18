package com.example.a4350_project_1;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private WeatherFragment.FetchWeatherTask mFetchWeatherTask = new FetchWeatherTask();
    private WeatherData mWeatherData;

    public WeatherFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFetchWeatherTask.setWeakReference(this);

        //Inflate the detail view
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        //Get the text view
//        mTvItemDetail = (TextView) view.findViewById(R.id.tvWeatherTitle);

        //Get the incoming detail text
        String detailString = getArguments().getString("item_detail");

        if (detailString != null) { }

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        loadWeatherData(sp.getString("location", ""));

        return view;
    }

    @Override
    public void onClick(View view) {

    }

    private void loadWeatherData(String location){
        mFetchWeatherTask.execute(this,location);
    }


    private class FetchWeatherTask {
        WeakReference<WeatherFragment> weatherFragmentWeakReference;
        private ExecutorService executorService = Executors.newSingleThreadExecutor();
        private Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        public void setWeakReference(WeatherFragment ref) {
            weatherFragmentWeakReference = new WeakReference<WeatherFragment>(ref);
        }

        public void execute(WeatherFragment ref, String location) {

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String jsonWeatherData;
                    URL weatherDataURL = NetworkUtils.buildURLFromString(location);
                    jsonWeatherData = null;
                    try {
                        jsonWeatherData = NetworkUtils.getDataFromURL(weatherDataURL);
                        postToMainThread(jsonWeatherData, location);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        private void postToMainThread(String jsonWeatherData, String location){
            WeatherFragment localRef = weatherFragmentWeakReference.get();
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
                            tvWeatherSubtitle = (TextView) getView().findViewById(R.id.tvWeatherSubtitle);
                            tvTempVal = (TextView) getView().findViewById(R.id.tvTempValue);
                            tvMaxTempVal = (TextView) getView().findViewById(R.id.tvMaxTempValue);
                            tvMinTempVal = (TextView) getView().findViewById(R.id.tvMinTempValue);
                            tvHumidityVal = (TextView) getView().findViewById(R.id.tvHumidityValue);
                            tvPressureVal = (TextView) getView().findViewById(R.id.tvPressureValue);
                            tvCloudsVal = (TextView) getView().findViewById(R.id.tvCloudValue);
                            tvSnowVal = (TextView) getView().findViewById(R.id.tvSnowValue);
                            tvRainVal = (TextView) getView().findViewById(R.id.tvRainValue);

                            int temp = roundToInt(kelvinToFahrenheit(localRef.mWeatherData.getTemperature().getTemp()));
                            int maxTemp = roundToInt(kelvinToFahrenheit(localRef.mWeatherData.getTemperature().getMaxTemp()));
                            int minTemp = roundToInt(kelvinToFahrenheit(localRef.mWeatherData.getTemperature().getMinTemp()));
                            int humidity = roundToInt(localRef.mWeatherData.getCurrentCondition().getHumidity());
                            int pressure = roundToInt(localRef.mWeatherData.getCurrentCondition().getPressure());
                            int cloudPercentage = roundToInt(localRef.mWeatherData.getClouds().getPerc());
                            int snowAmount = roundToInt(localRef.mWeatherData.getSnow().getAmount());
                            int rainAmount = roundToInt(localRef.mWeatherData.getRain().getAmount());

                            tvWeatherSubtitle.setText("Weather information around " + location + ":");
                            tvTempVal.setText(temp + " F");
                            tvMaxTempVal.setText(maxTemp + " F");
                            tvMinTempVal.setText(minTemp + " F");
                            tvHumidityVal.setText(humidity + "%");
                            tvPressureVal.setText(pressure + " hPa");
                            tvCloudsVal.setText(cloudPercentage + "%");
                            tvSnowVal.setText(snowAmount + " mm");
                            tvRainVal.setText(rainAmount + " mm");

//                            localRef.mTvTemp.setText("" + Math.round(localRef.mWeatherData.getTemperature().getTemp() - 273.15) + " C");
//                            localRef.mTvHum.setText("" + localRef.mWeatherData.getCurrentCondition().getHumidity() + "%");
//                            localRef.mTvPress.setText("" + localRef.mWeatherData.getCurrentCondition().getPressure() + " hPa");
                        }
                    }
                }
            });
        }
    }

    // = = = Helper methods = = =
    private int kelvinToFahrenheit(double temp){
        return (int)(1.8 * (temp - 273.15) + 32);
    }

    private int roundToInt(double temp){
        return (int)Math.round(temp);
    }

}
