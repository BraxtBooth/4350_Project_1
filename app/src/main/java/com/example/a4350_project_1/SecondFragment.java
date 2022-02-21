package com.example.a4350_project_1;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a4350_project_1.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        EditText nameText = (EditText) getView().findViewById(R.id.editTextName);
        EditText ageText = (EditText) getView().findViewById(R.id.editTextNumber);
        EditText locationText = (EditText) getView().findViewById(R.id.editTextLocation);
        NumberPicker heightFeet = (NumberPicker) getView().findViewById(R.id.editHeightFeet);
        NumberPicker heightInches = (NumberPicker) getView().findViewById(R.id.editHeightInches);
        EditText weightText = (EditText) getView().findViewById(R.id.editWeightNumber);
        Spinner sexText = (Spinner) getView().findViewById(R.id.sexSpinner);
        Spinner goalsText = (Spinner) getView().findViewById(R.id.spinnerGoals);
        Spinner activityText = (Spinner) getView().findViewById(R.id.activitySpinner);

        heightFeet.setMinValue(1);
        heightFeet.setMaxValue(10);
        heightInches.setMinValue(0);
        heightInches.setMaxValue(11);

        String nameValue = sp.getString("name", "Name");
        String ageValue = sp.getString("age", "Age");
        String locationValue = sp.getString("location", "Location");
        Integer heightFeetValue = sp.getInt("feet", 0);
        Integer heightInchesValue = sp.getInt("inches", 0);
        String weightValue = sp.getString("weight", "Weight");
        Integer sexValue = sp.getInt("sex", 0);
        Integer goalsValue = sp.getInt("goals", 0);
        Integer activityValue = sp.getInt("activity", 0);

        nameText.setText(nameValue);
        ageText.setText(ageValue);
        locationText.setText(locationValue);
        heightFeet.setValue(heightFeetValue);
        heightInches.setValue(heightInchesValue);
        weightText.setText(weightValue);
        sexText.setSelection(sexValue);
        goalsText.setSelection(goalsValue);
        activityText.setSelection(activityValue);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        // Save data from the user to the SavedPreferences
        binding.saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", nameText.getText().toString());
                editor.putString("age", ageText.getText().toString());
                editor.putString("location", locationText.getText().toString());
                editor.putInt("feet", heightFeet.getValue());
                editor.putInt("inches", heightInches.getValue());
                editor.putString("weight", weightText.getText().toString());
                editor.putInt("sex", sexText.getSelectedItemPosition());
                editor.putInt("goals", goalsText.getSelectedItemPosition());
                editor.putInt("activity", activityText.getSelectedItemPosition());
                editor.apply();

                Activity activity = getActivity();
                Toast.makeText(activity, "Information Saved.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}