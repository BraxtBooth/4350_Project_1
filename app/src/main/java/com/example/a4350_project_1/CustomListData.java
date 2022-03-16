package com.example.a4350_project_1;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class CustomListData implements Parcelable {

    //Implement the creator method
    public static final Parcelable.Creator<CustomListData> CREATOR = new Parcelable.Creator<CustomListData>() {

        //Call the private constructor
        @Override
        public CustomListData createFromParcel(Parcel in) {
            return new CustomListData(in);
        }

        @Override
        public CustomListData[] newArray(int size) {
            return new CustomListData[size];
        }

    };
    private List<String> mItemList;
    private List<String> mItemDetails;

    //Say how to read in from parcel
    private CustomListData(Parcel in) {
        in.readStringList(mItemList);
        in.readStringList(mItemDetails);
    }

//    public CustomListData(int numItems) {
//        //Populate the item list with data
//        //and populate the details list with details at the same time
//        mItemList = new ArrayList<>();
//        mItemDetails = new ArrayList<>();
//        for (int i = 1; i <= numItems; i++) {
//            mItemList.add("Item " + i);
//            mItemDetails.add("Item " + i + " is awesome, and unique because of this random number: " + Math.random());
//        }
//    }
    // new one im making
    public CustomListData() {
        //Populate the item list with data
        //and populate the details list with details at the same time
        mItemList = new ArrayList<>();
        mItemDetails = new ArrayList<>();
        mItemList.add("Profile");
        mItemDetails.add("Profile fragment");
        mItemList.add("Check BMI");
        mItemDetails.add("BMI fragment");
        mItemList.add("Check Weather Today");
        mItemDetails.add("Weather fragment");
        mItemList.add("Hikes near you >");
        mItemDetails.add("Hikes fragment");
    }

    //Say how and what to write to parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeStringList(mItemList);
        out.writeStringList(mItemDetails);
    }

    //Don't worry about this for now.
    @Override
    public int describeContents() {
        return 0;
    }

    //Implement a getter and setter for getting whole list
    public List<String> getItemList() {
        return mItemList;
    }

    public void setItemList(List<String> itemList) {
        mItemList = itemList;
    }

    //Implement getter for item details at a position
    public String getItemDetail(int position) {
        return mItemDetails.get(position);
    }
}
