package com.example.a4350_project_1;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class StepsActivity extends AppCompatActivity {

    private StepsFragment mStepsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        //Create the fragment
        mStepsFragment = new StepsFragment();

        //Pass data to the fragment
        mStepsFragment.setArguments(getIntent().getExtras());

        //No need to check if we're on a tablet. This activity only gets created on phones.
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.steps_activity_layout, mStepsFragment, "frag_stepsdetail");
        fTrans.commit();
    }
}
