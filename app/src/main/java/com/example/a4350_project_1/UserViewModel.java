package com.example.a4350_project_1;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {
    private LiveData<UserTable> userData;
    private Repository mRepository;

    public UserViewModel(Application application){
        super(application);
        mRepository = Repository.getInstance(application);
        userData = mRepository.getUserData();
    }

    public void setCurrentUser() { mRepository.setCurrentUser(); }

    public void updateUser(String email, String name, int age, String location, int feet, int inches,
                           int weight, int sex, int goal, int activity, int goalChange)
    {
        mRepository.updateUser(email, name, age, location, feet, inches, weight, sex, goal, activity, goalChange);
    }
    public void updateUserImageURI(String imageURI) { mRepository.updateUserStringURI(imageURI); }
    public void updateUserSteps(String steps) { mRepository.updateUserSteps(steps); }
    public void updateUserDates(String dates) { mRepository.updateUserDates(dates); }

    public LiveData<UserTable> getUserData(){
        return userData;
    }

}
