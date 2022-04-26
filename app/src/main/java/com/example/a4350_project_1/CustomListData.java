package com.example.a4350_project_1;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class CustomListData{

    //Implement the creator method
//    public static final Parcelable.Creator<CustomListData> CREATOR = new Parcelable.Creator<CustomListData>() {
//
//        //Call the private constructor
//        @Override
//        public CustomListData createFromParcel(Parcel in) {
//            return new CustomListData(in);
//        }
//
//        @Override
//        public CustomListData[] newArray(int size) {
//            return new CustomListData[size];
//        }
//
//    };
    private List<String> modulesList;
    private List<String> modulesListDetails;

    //Say how to read in from parcel
    private CustomListData(Parcel in) {
        in.readStringList(modulesList);
        in.readStringList(modulesListDetails);
    }

    // new one im making
    public CustomListData() {
        //Populate the item list with data
        //and populate the details list with details at the same time
        modulesList = new ArrayList<>();
        modulesListDetails = new ArrayList<>();
        modulesList.add("Profile");
        modulesListDetails.add("Profile fragment");
        modulesList.add("Check BMI");
        modulesListDetails.add("BMI fragment");
        modulesList.add("Check Weather Today");
        modulesListDetails.add("Weather fragment");
        modulesList.add("Hikes near you >");
        modulesListDetails.add("Hikes fragment");
        modulesList.add("Gyms near you >");
        modulesListDetails.add("Gyms fragment");
        modulesList.add("Step Counter Logs");
        modulesListDetails.add("Step Counter fragment");
        modulesList.add("Help");
        modulesListDetails.add("Help fragment");
    }

    //Implement a getter and setter for getting whole list
    public List<String> getItemList() {
        return modulesList;
    }

    public void setItemList(List<String> itemList) {
        modulesList = itemList;
    }

    //Implement getter for item details at a position
    public String getItemDetail(int position) {
        return modulesListDetails.get(position);
    }
}
