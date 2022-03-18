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

    private TextView mTvItemDetail;
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
        mTvItemDetail = (TextView) view.findViewById(R.id.tvExample);

        //Get the incoming detail text
        String detailString = getArguments().getString("item_detail");

        if (detailString != null) {
            mTvItemDetail.setText(detailString);
        }

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
                        postToMainThread(jsonWeatherData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        private void postToMainThread(String jsonWeatherData){
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
//                            localRef.mTvTemp.setText("" + Math.round(localRef.mWeatherData.getTemperature().getTemp() - 273.15) + " C");
//                            localRef.mTvHum.setText("" + localRef.mWeatherData.getCurrentCondition().getHumidity() + "%");
//                            localRef.mTvPress.setText("" + localRef.mWeatherData.getCurrentCondition().getPressure() + " hPa");
                            int fahrenheit_val = (int)(Math.round(1.8 * (localRef.mWeatherData.getTemperature().getTemp() - 273.15) + 32));
                            Toast.makeText(getActivity(),  String.valueOf(fahrenheit_val) + " F", Toast.LENGTH_LONG).show();
                            Log.e("Temperature", String.valueOf(fahrenheit_val) + " F");
                            Log.e("Humidity", String.valueOf(localRef.mWeatherData.getCurrentCondition().getHumidity()) );
                            Log.e("Pressure", String.valueOf(localRef.mWeatherData.getCurrentCondition().getPressure()));
                            Log.e("MaxTemp", String.valueOf(localRef.mWeatherData.getTemperature().getMaxTemp()));
                            Log.e("MinTemp", String.valueOf(localRef.mWeatherData.getTemperature().getMinTemp()));
                            Log.e("Cloud Percentage", String.valueOf(localRef.mWeatherData.getClouds().getPerc()));
                            Log.e("Snow Amount", String.valueOf(localRef.mWeatherData.getSnow().getAmount()));
                            Log.e("Rain Amount", String.valueOf(localRef.mWeatherData.getRain().getAmount()));
//                            Toast.makeText(activity, outputText, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }



}
