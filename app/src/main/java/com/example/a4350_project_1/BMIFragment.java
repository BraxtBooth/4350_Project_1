package com.example.a4350_project_1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BMIFragment extends Fragment implements View.OnClickListener {

    private TextView mTvItemDetail;
    private Button mButton;

    public BMIFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the detail view
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        //Get the text view
        mTvItemDetail = (TextView) view.findViewById(R.id.tv);

        //Get the incoming detail text
        String detailString = getArguments().getString("item_detail");

        if (detailString != null) {
            mTvItemDetail.setText(detailString);
        }

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
