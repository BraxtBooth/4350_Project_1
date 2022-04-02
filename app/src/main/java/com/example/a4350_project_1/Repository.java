package com.example.a4350_project_1;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static Repository instance;
    private final MutableLiveData<WeatherData> jsonData = new MutableLiveData<WeatherData>();
    private String mLocation;
    private String mJsonString;
    private WeatherDao mWeatherDao;
    // create userDao var

    private Repository(Application application){
        Database db = Database.getDatabase(application);
        mWeatherDao = db.weatherDao();
        //instantiate userDAO
        if(mLocation!=null)
            loadData();
    }

    public static synchronized Repository getInstance(Application application){
        if(instance == null){
            instance = new Repository(application);
        }
        return instance;
    }

    public void setLocation(String location){
        mLocation = location;
        loadData();
        insertWeatherInfo();
    }

    private void insertWeatherInfo(){
        if(mLocation!=null && mJsonString!=null) {
            WeatherTable weatherTable = new WeatherTableBuilder().setLocation(mLocation).setWeatherJson(mJsonString).createWeatherTable();
            Database.databaseExecutor.execute(() -> {
                mWeatherDao.insert(weatherTable);
            });
        }
    }

    public MutableLiveData<WeatherData> getData() {
        return jsonData;
    }

    private void loadData(){
        new FetchWeatherTask().execute(mLocation);
    }

    private class FetchWeatherTask{
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        public void execute(String location)
        {
            executorService.execute(new Runnable(){
                @Override
                public void run(){
                    String jsonWeatherData;
                    URL weatherDataURL = NetworkUtils.buildURLFromString(location);
                    jsonWeatherData = null;
                    try{
                        jsonWeatherData = NetworkUtils.getDataFromURL(weatherDataURL);
                        if(jsonWeatherData!=null)
                            mJsonString = jsonWeatherData;
                            postToMainThread(jsonWeatherData);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

        private void postToMainThread(String retrievedJsonData)
        {
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        jsonData.setValue(JSONWeatherUtils.getWeatherData(retrievedJsonData));
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
