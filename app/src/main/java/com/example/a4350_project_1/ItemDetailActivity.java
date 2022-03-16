package com.example.a4350_project_1;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ItemDetailActivity extends AppCompatActivity {

    private ItemDetailFragment mItemDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        //Create the fragment
        mItemDetailFragment = new ItemDetailFragment();

        //Pass data to the fragment
        mItemDetailFragment.setArguments(getIntent().getExtras());

        //No need to check if we're on a tablet. This activity only gets created on phones.
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fl_frag_itemdetail_container_phone, mItemDetailFragment, "frag_itemdetail");
        fTrans.commit();
    }
}
