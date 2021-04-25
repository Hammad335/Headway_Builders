package com.codewithhamad.headwaybuilders;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.codewithhamad.headwaybuilders.adapters.AllWorkersAdapter;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.WorkerModel;
import java.util.ArrayList;

public class AllWorkersFragment extends Fragment {

    private RecyclerView recyclerView;
    private AllWorkersAdapter allWorkersAdapter;
    private DatabaseHelper databaseHelper;
    private ArrayList<WorkerModel> workers;
    private ImageView backBtn, sortBy;
    private TextView totalWorkers, totalSalary;
    private int totalNumberOfWorkers;
    private double totalSalaryGiven;
    private int buildingId= -1;
    private Bundle bundle;
    private boolean isBuildingDetailsFragment= false;



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
        totalWorkers= view.findViewById(R.id.totalNumberOfWorkers);
        totalSalary= view.findViewById(R.id.totalSalaryGiven);



        // retrieving data from database and setting adapters
        try{
            databaseHelper= new DatabaseHelper(getContext());

            Bundle bundle= this.getArguments();
            if(bundle!=null) {
                buildingId = bundle.getInt("id");
                if (buildingId != 0 && buildingId != -1) {
                    workers = databaseHelper.getAllFromWorkersTableByBuildingId(buildingId);
                }
                else
                    workers = databaseHelper.getAllFromWorkersTable(COLUMN_WORKER_NAME + " Collate NOCASE ASC");
            }
            else
                workers= databaseHelper.getAllFromWorkersTable(COLUMN_WORKER_NAME + " Collate NOCASE ASC");

            if (workers != null) {
                allWorkersAdapter = new AllWorkersAdapter(getContext(), workers, onRefreshWorkerFragmentInterface);
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
                // navigating back to the HomeFragment/BuildingDetailsFragment
                AppCompatActivity appCompatActivity= (AppCompatActivity) v.getContext();

                bundle= getArguments();
                if(bundle!=null){
                    boolean goBackToBuildingDetailsFrag= bundle.getBoolean("shouldGoBackToBuildingDetailsFrag");

                     if(goBackToBuildingDetailsFrag){

                         String jsonItemBuilding= bundle.getString("building");
                         String order= bundle.getString("order");
                         BuildingDetailsFragment buildingDetailsFragment= new BuildingDetailsFragment();
                         bundle.putString("building", jsonItemBuilding);
                         bundle.putString("order", order);
                         buildingDetailsFragment.setArguments(bundle);

                         if(getActivity().toString().toLowerCase().contains("analystactivity"))
                            appCompatActivity.getSupportFragmentManager().beginTransaction().
                                    replace(R.id.analystContainerFrameLayout, buildingDetailsFragment).commit();
                        else if(getActivity().toString().toLowerCase().contains("manageractivity"))
                            appCompatActivity.getSupportFragmentManager().beginTransaction().
                                    replace(R.id.managerContainerFrameLayout, buildingDetailsFragment).commit();
                    }
                     else{
                         if(getActivity().toString().toLowerCase().contains("analystactivity"))
                             appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.analystContainerFrameLayout, new HomeFragment()).commit();
                         else if(getActivity().toString().toLowerCase().contains("manageractivity"))
                             appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.managerContainerFrameLayout, new HomeFragment()).commit();
                     }
                }
                else{

                    if(getActivity().toString().toLowerCase().contains("analystactivity"))
                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.analystContainerFrameLayout, new HomeFragment()).commit();
                    else if(getActivity().toString().toLowerCase().contains("manageractivity"))
                        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.managerContainerFrameLayout, new HomeFragment()).commit();
                }


            }
        });

        sortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] sortOptions= {"Name", "Greatest Salary", "Lowest Salary"};

                bundle= getArguments();
                if(bundle != null){
                    String callingFragment= bundle.getString("callingFragment");
                    if(callingFragment!=null && callingFragment.equals("buildingDetailsFragment")){
                        isBuildingDetailsFragment= true;
                    }
                }

                AlertDialog.Builder builder= new AlertDialog.Builder(getContext())
                        .setTitle("Sort By")
                        .setItems(sortOptions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // on selecting sorting item
                                if(which==0){
                                    //sort by name
                                    if(isBuildingDetailsFragment && buildingId != 0 && buildingId != -1)
                                        allWorkersAdapter.setWorkers(databaseHelper.getAllFromWorkersTableByBuildingId(buildingId, COLUMN_WORKER_NAME + " Collate NOCASE ASC"));
                                    else
                                        allWorkersAdapter.setWorkers(databaseHelper.getAllFromWorkersTable(COLUMN_WORKER_NAME + " Collate NOCASE ASC"));
                                }
                                else if(which==1){
                                    //sort by greatest salary
                                    if(isBuildingDetailsFragment && buildingId != 0 && buildingId != -1)
                                        allWorkersAdapter.setWorkers(databaseHelper.getAllFromWorkersTableByBuildingId(buildingId, COLUMN_WORKER_SAL + " DESC"));
                                    else
                                        allWorkersAdapter.setWorkers(databaseHelper.getAllFromWorkersTable(COLUMN_WORKER_SAL + " DESC"));
                                }
                                else if(which==2){
                                    // sort by lowest salary
                                    if(isBuildingDetailsFragment && buildingId != 0 && buildingId != -1)
                                        allWorkersAdapter.setWorkers(databaseHelper.getAllFromWorkersTableByBuildingId(buildingId, COLUMN_WORKER_SAL + " ASC"));
                                    else
                                        allWorkersAdapter.setWorkers(databaseHelper.getAllFromWorkersTable(COLUMN_WORKER_SAL + " ASC"));
                                }

                            }
                        });
                builder.create().show();
            }
        });



        setTextViews();





        return view;
    }

    private void setTextViews() {

        if(buildingId!=0 && buildingId!=-1){
            totalNumberOfWorkers= databaseHelper.getTotalWorkersFromWorkerTableByBuildingId(buildingId);
            totalSalaryGiven= databaseHelper.getTotalSalaryFromWorkerTableByBuildingId(buildingId);
        }
        else {
            totalNumberOfWorkers = databaseHelper.getTotalWorkersFromWorkerTable();
            totalSalaryGiven= databaseHelper.getTotalSalaryFromWorkerTable();
        }

        // setting total number of workers to the textView
        if(totalNumberOfWorkers != -1)
            totalWorkers.setText(totalNumberOfWorkers + "");
        else
            totalWorkers.setText("0");

        // setting total salary given to the textView
        if(totalSalaryGiven != -1)
            totalSalary.setText(totalSalaryGiven + "");
        else
            totalSalary.setText("0.0");
    }


    OnRefreshWorkerFragmentInterface onRefreshWorkerFragmentInterface = new OnRefreshWorkerFragmentInterface() {
        @Override
        public void refreshView() {
            setTextViews();
        }
    };



}