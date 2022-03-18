package com.example.a4350_project_1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class BMIFragment extends Fragment implements View.OnClickListener {

    private TextView tvBMIDescription;
    private TextView tvBMI;
    private Button mButton;

    public BMIFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the detail view
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        //Get the text view
        tvBMIDescription = (TextView) view.findViewById(R.id.tv_BMI_info);
        tvBMIDescription.setText("Body Mass Index (BMI) is a person’s weight in kilograms divided " +
                "by the square of height in meters. A high BMI can indicate high body fatness. BMI " +
                "screens for weight categories that may lead to health problems, but it does not " +
                "diagnose the body fatness or health of an individual.");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String outputText = "Please enter height and weight on profile page";

        float feet = sp.getInt("feet", 0);
        float inches = sp.getInt("inches", 0);
        float weight = sp.getInt( "weight", 0);

        if(feet !=0 && weight != 0){
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            float totalHeight = feet*12 + inches;
            float bmi = (weight/(totalHeight*totalHeight))*703;
            outputText = "Your BMI is: " + String.valueOf(df.format(bmi) + "");
        }

        tvBMI = (TextView) view.findViewById(R.id.tvBMI);
        tvBMI.setText(outputText);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
