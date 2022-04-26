package com.example.a4350_project_1;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.amplifyframework.AmplifyException;

import org.json.JSONException;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static Repository instance;
    private final MutableLiveData<WeatherData> jsonWeatherData = new MutableLiveData<WeatherData>();
    private String mWeatherLocation;
    private String mJsonWeatherString;
    private WeatherDao mWeatherDao;

    private LiveData<UserTable> currentUser = new MutableLiveData<UserTable>();
    private String userName;
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

    public void setCurrentUser(){
        loadUserData();
    }

    public void updateUser(String email, String name, int age, String location, int feet, int inches,
                           int weight, int sex, int goal, int activity, int goalChange){
        Database.databaseExecutor.execute(() -> {
            userDao.updateUser(email, name, age, location, feet, inches, weight, sex, goal, activity, goalChange);
        });
        try{
            AWSAmplify.uploadDBFile();
        } catch (Exception e){
            Log.e("Amplify", "User insert amplify error", e);
        }
    }

    public void updateUserStringURI(String imageURI){
        Database.databaseExecutor.execute(() -> {
            userDao.updateUserImageURI(imageURI);
        });
    }

    public void updateUserDates(String dates){
        Database.databaseExecutor.execute(() -> {
            userDao.updateUserDates(dates);
        });
    }

    public void updateUserSteps(String steps){
        Database.databaseExecutor.execute(() -> {
            userDao.updateUserSteps(steps);
        });
        try{
            AWSAmplify.uploadDBFile();
        } catch (Exception e){
            Log.e("Amplify", "Step Count insert amplify error", e);
        }
    }

    private void insertWeatherInfo(){
        if(mWeatherLocation != null && mJsonWeatherString != null) {
            WeatherTable weatherTable = new WeatherTableBuilder().setLocation(mWeatherLocation).setWeatherJson(mJsonWeatherString).createWeatherTable();
            Database.databaseExecutor.execute(() -> {
                mWeatherDao.insert(weatherTable);
            });
        }
        try{
            AWSAmplify.uploadDBFile();
        } catch (Exception e){
            Log.e("Amplify", "Weather insert amplify error", e);
        }
    }

    private void insertUserInfo(){
        if(userName != null) {

        }
    }

    public void insertNewUser(UserTable newUser){

    }

    public MutableLiveData<WeatherData> getWeatherData() {
        return jsonWeatherData;
    }

    public LiveData<UserTable> getUserData() {
        return currentUser;
    }

    private void loadWeatherData(){
        new FetchWeatherTask().executeWeatherFetch(mWeatherLocation);
    }

    private void loadUserData(){
        Database.databaseExecutor.execute(() -> {
            currentUser = userDao.getUser();
        });
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
