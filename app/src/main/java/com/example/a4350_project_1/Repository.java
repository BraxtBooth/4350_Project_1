package com.example.a4350_project_1;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static Repository instance;
    private final MutableLiveData<WeatherData> jsonWeatherData = new MutableLiveData<WeatherData>();
    private String mWeatherLocation;
    private String mJsonWeatherString;
    private WeatherDao mWeatherDao;

    private final MutableLiveData<UserTable> userData = new MutableLiveData<UserTable>();
    private String userName;
//    private String userString;
    private UserDao userDao;

    private Repository(Application application){
        Database db = Database.getDatabase(application);
        mWeatherDao = db.weatherDao();
        userDao = db.userDao();
        if(mWeatherLocation !=null)
            loadWeatherData();

        if(userName != null) loadUserData();
    }

    public static synchronized Repository getInstance(Application application){
        if(instance == null){
            instance = new Repository(application);
        }
        return instance;
    }

    public void setWeatherLocation(String location){
        mWeatherLocation = location;
        loadWeatherData();
        insertWeatherInfo();
    }

    public void setUserName(String name){
        userName = name;
        loadUserData();
        insertUserInfo();
    }

    private void insertWeatherInfo(){
        if(mWeatherLocation !=null && mJsonWeatherString !=null) {
            WeatherTable weatherTable = new WeatherTableBuilder().setLocation(mWeatherLocation).setWeatherJson(mJsonWeatherString).createWeatherTable();
            Database.databaseExecutor.execute(() -> {
                mWeatherDao.insert(weatherTable);
            });
        }
    }

    private void insertUserInfo(){
//        if(userName !=null && userString !=null) {
//            UserTable userTable = new UserTableBuilder().setLocation(mWeatherLocation).setWeatherJson(mJsonWeatherString).createWeatherTable();
//            Database.databaseExecutor.execute(() -> {
//                mWeatherDao.insert(userTable);
//            });
//        }
    }

    public MutableLiveData<WeatherData> getWeatherData() {
        return jsonWeatherData;
    }

    public MutableLiveData<UserTable> getUserData() {
        return userData;
    }

    private void loadWeatherData(){
        new FetchWeatherTask().executeWeatherFetch(mWeatherLocation);
    }

    private void loadUserData(){
        LiveData<UserTable> userData = userDao.getUser(userName);
    }

    private class FetchWeatherTask{
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        public void executeWeatherFetch(String location)
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
                            mJsonWeatherString = jsonWeatherData;
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
                        jsonWeatherData.setValue(JSONWeatherUtils.getWeatherData(retrievedJsonData));
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
