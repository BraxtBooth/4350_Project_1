package com.example.a4350_project_1;

import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

public class HeadActivity extends AppCompatActivity
        implements MyRVAdapter.DataPasser{

    private static final CustomListData mCustomListData  = new CustomListData();
    private MasterListFragment mMasterListFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);

        //Put this into a bundle
        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putParcelable("item_list", mCustomListData);

        //Create the fragment
        mMasterListFragment = new MasterListFragment();

        //Pass data to the fragment
        mMasterListFragment.setArguments(fragmentBundle);

        //If we're on a tablet, the master fragment appears on the left pane. If we're on a phone,
        //it takes over the whole screen
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        if(isTablet()){
            // replace dummy fragment in xml with mMasterListFragment
            fTrans.replace(R.id.masterlist_container_tablet, mMasterListFragment,"frag_masterlist");
        } else {
            fTrans.replace(R.id.masterlist_container_phone, mMasterListFragment, "frag_masterlist");
        }
        fTrans.commit();
    }

    //This receives the position of the clicked item in the MasterListFragment's RecyclerView
    @Override
    public void passData(int position) {
        //Get the string data corresponding to the detail view
        String itemDetailString = mCustomListData.getItemDetail(position);

        //Put this into a bundle
        Bundle detailBundle = new Bundle();
        detailBundle.putString("item_detail",itemDetailString);

        //If we're on a tablet, the fragment occupies the second pane (right). If we're on a phone,
        //the fragment is on a new Activity
        if(isTablet()) {
            if(position == 0){
                Fragment profileFragment = new ProfileFragment();
//                profileFragment.setArguments(detailBundle);
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.itemdetail_container_tablet, profileFragment, "profile-fragment");
                fTrans.commit();
            }
            else if(position == 1){
                Fragment bmiFragment = new BMIFragment();
                bmiFragment.setArguments(detailBundle);
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.itemdetail_container_tablet, bmiFragment, "bmi-fragment");
                fTrans.commit();
            }
            else if(position == 2){
                Fragment weatherFragment = new WeatherFragment();
                weatherFragment.setArguments(detailBundle);
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.itemdetail_container_tablet, weatherFragment, "weather-fragment");
                fTrans.commit();
            }
            else if(position == 3){
                String searchString = "Hikes near me";
                Uri searchUri = Uri.parse("geo:0,0?q=" + searchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
            else if(position == 4){
                String searchString = "Gym near me";
                Uri searchUri = Uri.parse("geo:0,0?q=" + searchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
            else{
                //Create a new detail fragment
                ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
                //Pass data to the fragment
                itemDetailFragment.setArguments(detailBundle);
                //Replace the detail fragment container
                FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                fTrans.replace(R.id.itemdetail_container_tablet, itemDetailFragment, "frag_itemdetail");
                fTrans.commit();
            }
        }

        else{ //On a phone
            if(position == 0){
                Intent sendIntent = new Intent(this, ProfileActivity.class);
                sendIntent.putExtras(detailBundle);
                startActivity(sendIntent);
            }
            else if(position == 1){
                Intent sendIntent = new Intent(this, BMIActivity.class);
                sendIntent.putExtras(detailBundle);
                startActivity(sendIntent);
            }
            else if(position == 2){
                Intent sendIntent = new Intent(this, WeatherActivity.class);
                sendIntent.putExtras(detailBundle);
                startActivity(sendIntent);
            }
            else if(position == 3){
                String searchString = "Hikes near me";
                Uri searchUri = Uri.parse("geo:0,0?q=" + searchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
            else if(position == 4){
                String searchString = "Gym near me";
                Uri searchUri = Uri.parse("geo:0,0?q=" + searchString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
            else{
                Intent sendIntent = new Intent(this, ItemDetailActivity.class);
                sendIntent.putExtras(detailBundle);
                startActivity(sendIntent);
            }
        }
    }

    boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }
}
