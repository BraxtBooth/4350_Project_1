package com.example.a4350_project_1;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<UserTable> userData;
    private Repository mRepository;

    public UserViewModel(Application application){
        super(application);
        mRepository = Repository.getInstance(application);
        userData = mRepository.getUserData();
    }
    public void setUsername(String name){
        mRepository.setUserName(name);
    }

    public LiveData<UserTable> getUserData(){
        return userData;
    }

}
