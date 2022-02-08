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
        EditText heightText = (EditText) getView().findViewById(R.id.editTextHeight);
        EditText weightText = (EditText) getView().findViewById(R.id.editTextWeight);
        EditText sexText = (EditText) getView().findViewById(R.id.editTextSex);
        Spinner goalsText = (Spinner) getView().findViewById(R.id.spinnerGoals);

        String nameValue = sp.getString("name", "Name");
        String ageValue = sp.getString("age", "Age");
        String locationValue = sp.getString("location", "Location");
        String heightValue = sp.getString("height", "Height");
        String weightValue = sp.getString("weight", "Weight");
        String sexValue = sp.getString("sex", "Sex");
        Integer goalsValue = sp.getInt("goals", 0);

        nameText.setText(nameValue);
        ageText.setText(ageValue);
        locationText.setText(locationValue);
        heightText.setText(heightValue);
        weightText.setText(weightValue);
        sexText.setText(sexValue);
        goalsText.setSelection(goalsValue);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        // save data from the user to the SavedPreferences
        binding.saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name", nameText.getText().toString());
                editor.putString("age", ageText.getText().toString());
                editor.putString("location", locationText.getText().toString());
                editor.putString("height", heightText.getText().toString());
                editor.putString("weight", weightText.getText().toString());
                editor.putString("sex", sexText.getText().toString());
                editor.putInt("goals", goalsText.getSelectedItemPosition());
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