package com.codewithhamad.headwaybuilders;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codewithhamad.headwaybuilders.analyst.AnalystActivity;
import com.codewithhamad.headwaybuilders.analyst.analystaddfrag.AddWorkerFragment;
import com.codewithhamad.headwaybuilders.analyst.analystaddfrag.AnalystAddFragment;
import com.codewithhamad.headwaybuilders.analyst.analysteditfrag.AnalystEditFragment;
import com.codewithhamad.headwaybuilders.analyst.analysteditfrag.EditBuildingFragment;
import com.codewithhamad.headwaybuilders.main.MainActivity;
import com.codewithhamad.headwaybuilders.models.BuildingModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class BuildingDetailsFragment extends Fragment {

    ImageView imageView, backBtn;
    TextView buildingId, buildingName, buildingType, buildingLocation, buildingArea, noOfFlats, noOfFloors, noOfLifts, parkingArea, details;
    Button btnAddWorker, btnEditBuilding;
    BuildingModel buildingModel;
    Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_building_details, container, false);

        // init views
        backBtn= view.findViewById(R.id.backBtn);
        imageView= view.findViewById(R.id.buildngDetailsImageView);
        buildingId= view.findViewById(R.id.buildingDetailsBId);
        buildingName= view.findViewById(R.id.buildingDetailsBName);
        buildingType= view.findViewById(R.id.buildingDetailsBType);
        buildingLocation= view.findViewById(R.id.buildingDetailsBLocation);
        buildingArea= view.findViewById(R.id.buildingDetailsBArea);
        noOfFlats= view.findViewById(R.id.buildingDetailsBNoOfFlats);
        noOfFloors= view.findViewById(R.id.buildingDetailsBNoOfFloors);
        noOfLifts= view.findViewById(R.id.buildingDetailsBNoOfLifts);
        parkingArea= view.findViewById(R.id.buildingDetailsBParkingArea);
        details= view.findViewById(R.id.buildingDetailsBShortDetails);
        btnEditBuilding= view.findViewById(R.id.btnEditBuilding);
        btnAddWorker= view.findViewById(R.id.btnAddWorker);

        // receiving data from BuildingAdapter
        bundle= this.getArguments();

        if(bundle!=null) {
            String jsonItem= bundle.getString("building");
            if(jsonItem != null){
                Gson gson = new Gson();
                Type type = new TypeToken<BuildingModel>() {}.getType();
                buildingModel = gson.fromJson(jsonItem, type);

                if(buildingModel != null){

                    // setting data to views
                     imageView.setImageBitmap(buildingModel.getBuildingImage());
                     buildingId.setText(buildingModel.getBuildingId() + "");
                     buildingName.setText(buildingModel.getBuildingName());
                     buildingType.setText(buildingModel.getBuildingType());
                     buildingLocation.setText(buildingModel.getBuildingLocation());
                     buildingArea.setText(buildingModel.getBuildingAreaInSqFt() + "");


                    if(buildingModel.getNumbOfFlats() == -1)
                        noOfFlats.setText("NULL");
                    else
                        noOfFlats.setText(buildingModel.getNumbOfFlats() + "");

                    if(buildingModel.getNumbOfFloors() == -1)
                        noOfFloors.setText("NULL");
                    else
                        noOfFloors.setText(buildingModel.getNumbOfFloors() + "");

                    if(buildingModel.getNumbOfLifts() == -1)
                        noOfLifts.setText("NULL");
                    else
                        noOfLifts.setText(buildingModel.getNumbOfLifts() + "");


                    parkingArea.setText(buildingModel.getParkingAreaInSqFt() + "");
                    details.setText(buildingModel.getShortDetails());

                }

            }
        }



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigating back to the HomeFragment
                AppCompatActivity appCompatActivity= (AppCompatActivity) v.getContext();

                String callingActivity= bundle.getString("key");

                // getting building order from bundle - bundle received from BuildingAdapter
                String buildingOrder= bundle.getString("order");

                // for persistence of order of buildings
                // after going back in HomeFragment, the same order of buildings is maintained
                Bundle orderByBundle = new Bundle();
                orderByBundle.putString("order", buildingOrder);
                HomeFragment homeFragment= new HomeFragment();
                homeFragment.setArguments(orderByBundle);

                if(callingActivity.equals("AnalystActivity"))
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.analystContainerFrameLayout, homeFragment).commit();
                else if(callingActivity.equals("ManagerActivity"))
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.managerContainerFrameLayout, homeFragment).commit();
            }
        });

        if(getActivity().toString().toLowerCase().contains("manageractivity")){
            btnAddWorker.setVisibility(View.GONE);
            btnEditBuilding.setVisibility(View.GONE);
        }
        else{
            btnAddWorker.setVisibility(View.VISIBLE);
            btnEditBuilding.setVisibility(View.VISIBLE);
        }

        btnEditBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putInt("id", buildingModel.getBuildingId());
                AnalystEditFragment analystEditFragment= new AnalystEditFragment();
                analystEditFragment.setArguments(bundle);

                AnalystActivity.readableBottomBar.selectItem(2);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.analystContainerFrameLayout, analystEditFragment).commit();
            }
        });

        btnAddWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle.putInt("id", buildingModel.getBuildingId());
                AnalystAddFragment analystAddFragment= new AnalystAddFragment();
                analystAddFragment.setArguments(bundle);

                AnalystActivity.readableBottomBar.selectItem(1);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.analystContainerFrameLayout, analystAddFragment).commit();

            }
        });

        return view;

    }
}