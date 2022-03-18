package com.example.a4350_project_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        EditText nameText = (EditText) getView().findViewById(R.id.editTextName);
        EditText ageText = (EditText) getView().findViewById(R.id.editTextNumber);
        EditText locationText = (EditText) getView().findViewById(R.id.editTextLocation);
        NumberPicker heightFeet = (NumberPicker) getView().findViewById(R.id.editHeightFeet);
        NumberPicker heightInches = (NumberPicker) getView().findViewById(R.id.editHeightInches);
        EditText weightText = (EditText) getView().findViewById(R.id.editWeightNumber);
        Spinner sexText = (Spinner) getView().findViewById(R.id.sexSpinner);
        Spinner goalsText = (Spinner) getView().findViewById(R.id.spinnerGoals);
        Spinner activityText = (Spinner) getView().findViewById(R.id.activitySpinner);
        TextView textViewLbsChange = (TextView) getView().findViewById(R.id.textViewLbsChange);
        NumberPicker lbsChange = (NumberPicker) getView().findViewById(R.id.editLbsChange);

        heightFeet.setMinValue(1);
        heightFeet.setMaxValue(10);
        heightInches.setMinValue(0);
        heightInches.setMaxValue(11);
        lbsChange.setMinValue(1);
        lbsChange.setMaxValue(2);

        String nameValue = sp.getString("name", "John");
        Integer ageValue = sp.getInt("age", 25);
        String locationValue = sp.getString("location", "Salt Lake City, US");
        Integer heightFeetValue = sp.getInt("feet", 5);
        Integer heightInchesValue = sp.getInt("inches", 11);
        Integer weightValue = sp.getInt("weight", 175);
        Integer sexValue = sp.getInt("sex", 0);
        Integer goalsValue = sp.getInt("goals", 0);
        Integer activityValue = sp.getInt("activity", 0);
        Integer lbsChangeValue = sp.getInt( "lbschange", 0);

        nameText.setText(nameValue);
        ageText.setText(ageValue.toString());
        locationText.setText(locationValue);
        heightFeet.setValue(heightFeetValue);
        heightInches.setValue(heightInchesValue);
        weightText.setText(weightValue.toString());
        sexText.setSelection(sexValue);
        goalsText.setSelection(goalsValue);
        activityText.setSelection(activityValue);
        lbsChange.setValue(lbsChangeValue);

        goalsText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Integer goalIndex = goalsText.getSelectedItemPosition();
                        if (goalIndex == 0) {
                            textViewLbsChange.setText("Lbs to gain per week");
                            textViewLbsChange.setVisibility(View.VISIBLE);
                            lbsChange.setVisibility(View.VISIBLE);
                        }
                        else if (goalIndex == 1) {
                            textViewLbsChange.setText("Lbs to lose per week");
                            textViewLbsChange.setVisibility(View.VISIBLE);
                            lbsChange.setVisibility(View.VISIBLE);
                        }
                        else if (goalIndex == 2) {
                            lbsChange.setValue(0);
                            textViewLbsChange.setVisibility(View.GONE);
                            lbsChange.setVisibility(View.GONE);
                        }
                    }

                        @Override
                        public void onNothingSelected (AdapterView < ? > adapterView){

                        }
            }
        );

        FloatingActionButton uploadProfilePicture = (FloatingActionButton) getView().findViewById(R.id.uploadProfilePicture);
        uploadProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ProfileActivity)getActivity()).selectImage(getContext());
            }
        });

        // Save data from the user to the SavedPreferences
        Button saveProfileButton = (Button) getView().findViewById(R.id.saveProfileButton);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", nameText.getText().toString());
                editor.putInt("age", Integer.parseInt(String.valueOf(ageText.getText())));
                editor.putString("location", locationText.getText().toString());
                editor.putInt("feet", heightFeet.getValue());
                editor.putInt("inches", heightInches.getValue());
                editor.putInt("weight", Integer.parseInt(String.valueOf(weightText.getText())));
                editor.putInt("sex", sexText.getSelectedItemPosition());
                editor.putInt("goals", goalsText.getSelectedItemPosition());
                editor.putInt("activity", activityText.getSelectedItemPosition());
                editor.putInt("lbschange", lbsChange.getValue());
                editor.apply();

                Activity activity = getActivity();
                Toast.makeText(activity, "Information Saved.", Toast.LENGTH_LONG).show();

            }
        });

        // Save data from the user to the SavedPreferences
        Button backHome = (Button) getView().findViewById(R.id.button_backHome);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), HeadActivity.class);
                startActivity(i);
            }
        });
    }

    boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }

}