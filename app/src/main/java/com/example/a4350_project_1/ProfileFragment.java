package com.example.a4350_project_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {

    private UserTable userData;
    private UserViewModel userViewModel;

    EditText emailText;
    EditText nameText;
    EditText ageText;
    EditText locationText;
    NumberPicker heightFeet;
    NumberPicker heightInches;
    EditText weightText;
    Spinner sexText;
    Spinner goalsText;
    Spinner activityText;
    TextView textViewLbsChange;
    NumberPicker lbsChange;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserData().observe(getViewLifecycleOwner(), userObserver);

//        loadUserDataToScreen();
        return view;
    }

    final Observer<UserTable> userObserver = new Observer<UserTable>() {
        @Override
        public void onChanged(UserTable userTable) {
            emailText.setText(userTable.getEmail());
            nameText.setText(userTable.getName());
            ageText.setText(String.valueOf(userTable.getAge()));
            locationText.setText(userTable.getLocation());
            heightFeet.setValue(userTable.getFeet());
            heightInches.setValue(userTable.getInches());
            weightText.setText(String.valueOf(userTable.getWeight()));
            sexText.setSelection(userTable.getSex());
            goalsText.setSelection(userTable.getGoal());
            activityText.setSelection(userTable.getActivity());
            lbsChange.setValue(userTable.getGoalChange());
        }
    };

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        emailText = (EditText) getView().findViewById(R.id.editTextEmail);
        nameText = (EditText) getView().findViewById(R.id.editTextName);
        ageText = (EditText) getView().findViewById(R.id.editTextNumber);
        locationText = (EditText) getView().findViewById(R.id.editTextLocation);
        heightFeet = (NumberPicker) getView().findViewById(R.id.editHeightFeet);
        heightInches = (NumberPicker) getView().findViewById(R.id.editHeightInches);
        weightText = (EditText) getView().findViewById(R.id.editWeightNumber);
        sexText = (Spinner) getView().findViewById(R.id.sexSpinner);
        goalsText = (Spinner) getView().findViewById(R.id.spinnerGoals);
        activityText = (Spinner) getView().findViewById(R.id.activitySpinner);
        textViewLbsChange = (TextView) getView().findViewById(R.id.textViewLbsChange);
        lbsChange = (NumberPicker) getView().findViewById(R.id.editLbsChange);

        heightFeet.setMinValue(1);
        heightFeet.setMaxValue(10);
        heightInches.setMinValue(0);
        heightInches.setMaxValue(11);
        lbsChange.setMinValue(1);
        lbsChange.setMaxValue(2);

//        String nameValue = sp.getString("name", "John");
//        Integer ageValue = sp.getInt("age", 25);
//        String locationValue = sp.getString("location", "Salt Lake City, US");
//        Integer heightFeetValue = sp.getInt("feet", 5);
//        Integer heightInchesValue = sp.getInt("inches", 11);
//        Integer weightValue = sp.getInt("weight", 175);
//        Integer sexValue = sp.getInt("sex", 0);
//        Integer goalsValue = sp.getInt("goals", 0);
//        Integer activityValue = sp.getInt("activity", 0);
//        Integer lbsChangeValue = sp.getInt( "lbschange", 0);

//        nameText.setText(nameValue);
//        ageText.setText(ageValue.toString());
//        locationText.setText(locationValue);
//        heightFeet.setValue(heightFeetValue);
//        heightInches.setValue(heightInchesValue);
//        weightText.setText(weightValue.toString());
//        sexText.setSelection(sexValue);
//        goalsText.setSelection(goalsValue);
//        activityText.setSelection(activityValue);
//        lbsChange.setValue(lbsChangeValue);

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
            public void onNothingSelected (AdapterView < ? > adapterView){}
            }
        );

        FloatingActionButton uploadProfilePicture = (FloatingActionButton) getView().findViewById(R.id.uploadProfilePicture);
        uploadProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTablet()) ((HeadActivity)getActivity()).selectImage(getContext());
                else ((ProfileActivity)getActivity()).selectImage(getContext());
            }
        });

        // Save data from the user to the SavedPreferences
        Button saveProfileButton = (Button) getView().findViewById(R.id.saveProfileButton);
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("name", nameText.getText().toString());
//                editor.putInt("age", Integer.parseInt(String.valueOf(ageText.getText())));
//                editor.putString("location", locationText.getText().toString());
//                editor.putInt("feet", heightFeet.getValue());
//                editor.putInt("inches", heightInches.getValue());
//                editor.putInt("weight", Integer.parseInt(String.valueOf(weightText.getText())));
//                editor.putInt("sex", sexText.getSelectedItemPosition());
//                editor.putInt("goals", goalsText.getSelectedItemPosition());
//                editor.putInt("activity", activityText.getSelectedItemPosition());
//                editor.putInt("lbschange", lbsChange.getValue());
//                editor.apply();

                userViewModel.updateUser(emailText.getText().toString(), nameText.getText().toString(),
                        Integer.parseInt(String.valueOf(ageText.getText())), locationText.getText().toString(),
                                heightFeet.getValue(), heightInches.getValue(), Integer.parseInt(String.valueOf(weightText.getText())),
                                        sexText.getSelectedItemPosition(), goalsText.getSelectedItemPosition(),
                                        activityText.getSelectedItemPosition(), lbsChange.getValue() );

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