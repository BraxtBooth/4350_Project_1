package com.example.a4350_project_1;

import android.content.Context;

import androidx.fragment.app.Fragment;

import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.List;

public class MasterListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    public MasterListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_master_list, container, false);



        //Get the recycler view
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.rv_Master);

        //Tell Android that we know the size of the recyclerview doesn't change
        mRecyclerView.setHasFixedSize(true);

        //Set the layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        //Get data from main activity
        CustomListData customListData = getArguments().getParcelable("item_list");
        List<String> inputList = customListData.getItemList();

        //Set the adapter
        mAdapter = new MyRVAdapter(inputList);
        mRecyclerView.setAdapter(mAdapter);

        setRetainInstance(true);

        return fragmentView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // OLD CODE = = == = = == = = = = == = = = = == = = = ==  = == =  =


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        String imageUri = sp.getString("image", "");
        ImageView profileImg = (ImageView) getView().findViewById(R.id.ivProfileImage);
        if(imageUri == ""){
            profileImg.setImageResource(R.drawable.user_icon);
        }else{
            loadImageFromStorage(imageUri);
        }


//        SharedPreferences.Editor preferencesEditor = sp.edit();
//        preferencesEditor.putInt("feet", 6);
//        preferencesEditor.putInt("weight", 24);
//        preferencesEditor.putInt("age", 42);
//        preferencesEditor.apply();

        TextView BMRText = (TextView) getView().findViewById(R.id.tvMBRValue);
        TextView CaloriesText = (TextView) getView().findViewById(R.id.tvCaloriesValue);
        TextView CaloriesWarning = (TextView) getView().findViewById(R.id.tvCalorieWarning);
        String BMRTextOutput = "Please enter height, weight, age, and sex on profile page";
        String CaloriesTextOutput = "";
        float feet = sp.getInt("feet", 0);
        float inches = sp.getInt("inches", 0);
        float weight = sp.getInt( "weight", 0);
        float age = sp.getInt("age", 0);
        int sex = sp.getInt("sex", 0);
        String[] sexEntries = getResources().getStringArray(R.array.Sex_Array);
        int activityIndex = sp.getInt("activity", 0);
        int goalIndex = sp.getInt("goals", 0);
        Integer lbsChange = sp.getInt("lbschange", 0);
        float bmr = 0;
        float calorieFactor = (float) (activityIndex==0 ? 1.55 : 1.2);
        float calorieIntake = 0;

        if(feet !=0 && weight!=0 && age != 0){
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            float totalHeight = feet*12 + inches;
            if(sexEntries[sex].equals("Male")){
                bmr = (float) (66.47+(6.24*weight)+(12.7*totalHeight)-(6.755*age));
                BMRTextOutput = String.valueOf(df.format(bmr));
            }
            else if(sexEntries[sex].equals("Female")){
                bmr = (float) (655.1 + (4.35*weight) + (4.7*totalHeight) - (4.7*age));
                BMRTextOutput = String.valueOf(df.format(bmr));
            }
            else{
                BMRTextOutput = "Cannot calculate BMR based on Sex";
            }

            if(bmr != 0) {
                if (goalIndex == 0) {
                    //Gain Weight
                    calorieIntake = bmr*calorieFactor+(lbsChange==1 ? 500: 1000);
                    CaloriesTextOutput = String.valueOf(df.format(calorieIntake));

                } else if (goalIndex == 1) {
                    //Lose Weight
                    calorieIntake = bmr*calorieFactor-(lbsChange==1 ? 500: 1000);
                    CaloriesTextOutput = String.valueOf(df.format(calorieIntake));
                } else if (goalIndex == 2) {
                    //Maintain Weight
                    calorieIntake = bmr*calorieFactor;
                    CaloriesTextOutput = String.valueOf(df.format(calorieIntake));
                }
            }
        }
        BMRText.setText(BMRTextOutput);
        CaloriesText.setText(CaloriesTextOutput);

        if((calorieIntake < 1200 && sexEntries[sex].equals("Male")) || (calorieIntake<1000 && sexEntries[sex].equals("Females"))){
            CaloriesWarning.setVisibility(View.VISIBLE);
        }
        else{
            CaloriesWarning.setVisibility(View.GONE);
        }


        // OLD CODE = = == = = == = = = = == = = = = == = = = ==  = == =  =

    }

    private void loadImageFromStorage(String imgName)
    {
        ContextWrapper cw = new ContextWrapper(getContext());
        File directoryPath = cw.getDir("imageDir", Context.MODE_PRIVATE);

        try {
            File f = new File(directoryPath, imgName);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img = getView().findViewById(R.id.ivProfileImage);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
