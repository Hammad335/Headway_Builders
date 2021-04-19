package com.codewithhamad.headwaybuilders;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.codewithhamad.headwaybuilders.adapters.AllWorkersAdapter;
import com.codewithhamad.headwaybuilders.adapters.BuildingAdapter;
import com.codewithhamad.headwaybuilders.adapters.WorkerAdapter;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.WorkerModel;

import java.util.ArrayList;

public class AllWorkersFragment extends Fragment {

    private RecyclerView recyclerView;
    private AllWorkersAdapter allWorkersAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<WorkerModel> workers;
    private ImageView backBtn, sortBy;

    private String CALLING_ACTIVITY;

    private static final String COLUMN_WORKER_NAME = "worker_name";
    private static final String COLUMN_WORKER_SAL = "sal";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all_workers, container, false);


        // init views
        recyclerView= view.findViewById(R.id.fragAllWorkersRecView);
        backBtn= view.findViewById(R.id.backBtnAllWorkers);
        sortBy= view.findViewById(R.id.fragmentAllWorkersSortBy);

        if(getActivity().toString().toLowerCase().contains("manageractivity"))
            CALLING_ACTIVITY= "ManagerActivity";
        else if(getActivity().toString().toLowerCase().contains("analystactivity"))
            CALLING_ACTIVITY= "AnalystActivity";

        // retrieving data from database and setting adapters
        try{
            databaseHelper= new DatabaseHelper(getContext());
            workers= databaseHelper.getAllFromWorkersTable(COLUMN_WORKER_NAME + " Collate NOCASE ASC");

            if(workers != null){

                allWorkersAdapter= new AllWorkersAdapter(getContext(), workers, CALLING_ACTIVITY);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.setAdapter(allWorkersAdapter);
            }

        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigating back to the HomeFragment
                AppCompatActivity appCompatActivity= (AppCompatActivity) v.getContext();

                if(CALLING_ACTIVITY.equals("AnalystActivity"))
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.analystContainerFrameLayout, new HomeFragment()).commit();
                else if(CALLING_ACTIVITY.equals("ManagerActivity"))
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.managerContainerFrameLayout, new HomeFragment()).commit();
            }
        });

        sortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] sortOptions= {"Name", "Greatest Salary", "Lowest Salary", "Newest", "Oldest"};

                AlertDialog.Builder builder= new AlertDialog.Builder(getContext())
                        .setTitle("Sort By")
                        .setItems(sortOptions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // on selecting sorting item
                                if(which==0){
                                    //sort by name
                                    allWorkersAdapter.setWorkers(databaseHelper.getAllFromWorkersTable(COLUMN_WORKER_NAME + " Collate NOCASE ASC"));
                                }
                                else if(which==1){
                                    //sort by greatest salary
                                    allWorkersAdapter.setWorkers(databaseHelper.getAllFromWorkersTable(COLUMN_WORKER_SAL + " DESC"));
                                }
                                else if(which==2){
                                    // sort by lowest salary
                                    allWorkersAdapter.setWorkers(databaseHelper.getAllFromWorkersTable(COLUMN_WORKER_SAL + " ASC"));
                                }
                                else if(which==3){
                                    //sort by newest
                                }
                                else if(which==4){
                                    //sort by oldest

                                }

                            }
                        });
                builder.create().show();
            }
        });


        return view;
    }
}