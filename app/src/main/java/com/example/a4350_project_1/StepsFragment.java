package com.example.a4350_project_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class StepsFragment extends Fragment {

    UserViewModel userViewModel;
    String steps, dates;
    String[] steps_dates_array;
    ListView lvSteps;
    ArrayList<String> stepsArray, datesArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_steps, container, false);

        userViewModel = new ViewModelProvider( this).get(UserViewModel.class);
        steps = userViewModel.getUserData().getValue().getSteps();
        dates = userViewModel.getUserData().getValue().getDates();
        stepsArray = new ArrayList<>(Arrays.asList(steps.split("/")));
        Toast.makeText(getContext(), stepsArray.get(2), Toast.LENGTH_LONG).show();
        datesArray = new ArrayList<>(Arrays.asList(dates.split("/")));

        lvSteps = view.findViewById(R.id.lvSteps);
//        for(int i=0; i<stepsArray.size(); i++){
//
//        }
//        String[] steps = stepsArray.toArray(new String[stepsArray.size()]);
        ArrayAdapter<String> stepsListAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        for(int i=0; i<stepsArray.size(); i++){
            stepsListAdapter.add("Log Date: " + datesArray.get(i) + ", Steps: " + stepsArray.get(i));
        }
        lvSteps.setAdapter(stepsListAdapter);
        return view;
    }
}