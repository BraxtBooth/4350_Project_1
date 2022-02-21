package com.example.a4350_project_1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a4350_project_1.databinding.FragmentFirstBinding;

import java.text.DecimalFormat;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        TextView BMRText = (TextView) getView().findViewById(R.id.textViewBMRValue);
        TextView CaloriesText = (TextView) getView().findViewById(R.id.textViewCaloriesValue);
        EditText LbsToLoseText = (EditText) getView().findViewById(R.id.editLbsToLose);
        String BMRTextOutput = "Please enter height, weight, age, and sex on profile page";
        String CaloriesTextOutput = "";
        float feet = sp.getInt("feet", 0);
        float inches = sp.getInt("inches", 0);
        float weight = Integer.valueOf(sp.getString( "weight", "0"));
        float age = Integer.parseInt(sp.getString("age", "0"));
        int sex = sp.getInt("sex", 0);
        String[] sexEntries = getResources().getStringArray(R.array.Sex_Array);
        int activity = sp.getInt("activity", 0);
        String[] activityEntries = getResources().getStringArray(R.array.Activity_Array);

        String LbsToLoseValue = sp.getString("lbsToLose", "1");
        LbsToLoseText.setText(LbsToLoseValue);

        if(feet !=0 && weight!=0 && age != 0){
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            float totalHeight = feet*12 + inches;
            if(sexEntries[sex].equals("Male")){
                float bmr = (float) (66.47+(6.24*weight)+(12.7*totalHeight)-(6.755*age));
                BMRTextOutput = String.valueOf(df.format(bmr));
            }
            else if(sexEntries[sex].equals("Female")){
                float bmr = (float) (655.1 + (4.35*weight) + (4.7*totalHeight) - (4.7*age));
                BMRTextOutput = String.valueOf(df.format(bmr));
            }
            else{
                BMRTextOutput = "Cannot calculate BMR based on Sex";
            }


        }
        BMRText.setText(BMRTextOutput);
        CaloriesText.setText(CaloriesTextOutput);



        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.buttonBMI.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
                String outputText = "Please enter height and weight on profile page";

                float feet = sp.getInt("feet", 0);
                float inches = sp.getInt("inches", 0);
                float weight = Integer.valueOf(sp.getString( "weight", "0"));

                if(feet !=0 && weight != 0){
                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);
                    float totalHeight = feet*12 + inches;
                    float bmi = (weight/(totalHeight*totalHeight))*703;
                    outputText = "BMI: " + String.valueOf(df.format(bmi));
                }
                Activity activity = getActivity();
                Toast.makeText(activity, outputText, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}