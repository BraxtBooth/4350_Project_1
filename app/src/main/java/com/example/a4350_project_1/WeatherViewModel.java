package com.example.a4350_project_1;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<WeatherData> jsonData;
    private Repository mRepository;

    public WeatherViewModel(Application application){
        super(application);
        mRepository = Repository.getInstance(application);
        jsonData = mRepository.getWeatherData();
    }
    public void setLocation(String location){
        mRepository.setWeatherLocation(location);
    }

    public LiveData<WeatherData> getWeatherData(){
        return jsonData;
    }
}
