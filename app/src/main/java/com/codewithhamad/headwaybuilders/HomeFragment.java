package com.codewithhamad.headwaybuilders;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithhamad.headwaybuilders.adapters.BuildingAdapter;
import com.codewithhamad.headwaybuilders.adapters.WorkerAdapter;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.BuildingModel;
import com.codewithhamad.headwaybuilders.models.WorkerModel;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ImageView sortByBtn;
    private TextView txtShowAllWorkers;
    private RecyclerView workersRecView, buildingsRecView;
    private BuildingAdapter buildingAdapter;
    private WorkerAdapter workerAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<BuildingModel> buildings= new ArrayList<>();
    private ArrayList<WorkerModel> workers= new ArrayList<>();
    private String CALLING_ACTIVITY;

    private static final String COLUMN_BUILDING_DATETIME = "datetime";
    private static final String COLUMN_BUILDING_NAME = "name";
    private static final String COLUMN_BUILDING_AREA = "buildingAreaInSqFt";
    private String BUILDING_ORDER;

    private static final String COLUMN_WORKER_NAME = "worker_name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);


        // init views
        sortByBtn= view.findViewById(R.id.buildingsSortByBtn);
        txtShowAllWorkers= view.findViewById(R.id.txtShowAllWorkers);
        buildingsRecView= view.findViewById(R.id.homeFragBuildingsRecView);
        workersRecView= view.findViewById(R.id.homeFragWorkersRecView);


        if(getActivity().toString().toLowerCase().contains("manageractivity"))
            CALLING_ACTIVITY= "ManagerActivity";
        else if(getActivity().toString().toLowerCase().contains("analystactivity"))
            CALLING_ACTIVITY= "AnalystActivity";


        // in order to get buildings order received from BuildingDetailsFragment
        // otherwise default order= newest first
        Bundle bundle= this.getArguments();
        BUILDING_ORDER= (bundle!=null)? bundle.getString("order") : COLUMN_BUILDING_DATETIME + " DESC";;

        // retrieving data from database and setting adapters
        try{
            databaseHelper= new DatabaseHelper(getContext());
            buildings= databaseHelper.getAllFromBuildingsTable(BUILDING_ORDER);
            workers= databaseHelper.getAllFromWorkersTable(COLUMN_WORKER_NAME + " Collate NOCASE ASC");

            if(buildings != null){
                buildingAdapter = new BuildingAdapter(getContext(), buildings, CALLING_ACTIVITY, BUILDING_ORDER);
                buildingsRecView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                buildingsRecView.setAdapter(buildingAdapter);
            }

            if(workers != null){

                workerAdapter= new WorkerAdapter(getContext(), workers);
                workersRecView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                workersRecView.setAdapter(workerAdapter);
            }

        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        txtShowAllWorkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (workers != null) {

                    try {

                        if(CALLING_ACTIVITY.equals("AnalystActivity"))
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.analystContainerFrameLayout, new AllWorkersFragment()).commit();
                        else if(CALLING_ACTIVITY.equals("ManagerActivity"))
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.managerContainerFrameLayout, new AllWorkersFragment()).commit();

                    }
                    catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(getContext(), "No workers exist in database.", Toast.LENGTH_SHORT).show();
//




            }

        });

        sortByBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] sortOptions= {"Title", "Area", "Newest", "Oldest"};
                AlertDialog.Builder builder= new AlertDialog.Builder(getContext())
                        .setTitle("Sort By")
                        .setItems(sortOptions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // on selecting sorting item
                                if(which==0){
                                    //sort by title
                                    BUILDING_ORDER= COLUMN_BUILDING_NAME+" Collate NOCASE ASC";
                                    buildingAdapter.setBuildings(databaseHelper.getAllFromBuildingsTable(BUILDING_ORDER), BUILDING_ORDER);
                                }
                                else if(which==1){
                                    //sort by area
                                    BUILDING_ORDER= COLUMN_BUILDING_AREA+" ASC";
                                    buildingAdapter.setBuildings(databaseHelper.getAllFromBuildingsTable(BUILDING_ORDER), BUILDING_ORDER);
                                }
                                else if(which==2){
                                    //sort by newest
                                    BUILDING_ORDER= COLUMN_BUILDING_DATETIME + " DESC";
                                    buildingAdapter.setBuildings(databaseHelper.getAllFromBuildingsTable(BUILDING_ORDER), BUILDING_ORDER);
                                }
                                else if(which==3){
                                    //sort by oldest
                                    BUILDING_ORDER= COLUMN_BUILDING_DATETIME+" ASC";
                                    buildingAdapter.setBuildings(new DatabaseHelper(getContext()).getAllFromBuildingsTable(BUILDING_ORDER), BUILDING_ORDER);
                                }
                            }
                        });
                builder.create().show();

            }
        });


        return view;
    }


}