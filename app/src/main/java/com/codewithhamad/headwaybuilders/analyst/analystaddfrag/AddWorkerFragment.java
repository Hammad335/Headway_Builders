package com.codewithhamad.headwaybuilders.analyst.analystaddfrag;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.BuildingModel;
import com.codewithhamad.headwaybuilders.models.WorkerModel;

import java.util.ArrayList;

public class AddWorkerFragment extends Fragment {

    EditText workerId, buildingId, workerName, salary, job;
    Button addBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_worker, container, false);

        // init views
        workerId= view.findViewById(R.id.workerId);
        buildingId= view.findViewById(R.id.addWorkerBuildingId);
        workerName= view.findViewById(R.id.workerName);
        salary= view.findViewById(R.id.salary);
        job= view.findViewById(R.id.job);
        addBtn= view.findViewById(R.id.addWorkerAddBtn);


       Bundle bundle= this.getArguments();
       if(bundle!=null){
           if(bundle.getInt("id") != 0 && bundle.getInt("id") != -1){
               buildingId.setText(bundle.getInt("id") + "");

               try {

                   int building_id;
                   if (buildingId.getText().length() != 0) {

                       building_id = Integer.parseInt(buildingId.getText().toString());

                       if (new DatabaseHelper(getContext()).doesExistInBuildingTable(building_id)) {
                           buildingId.setTextColor(getResources().getColor(R.color.green));
                       } else {
                           buildingId.setTextColor(getResources().getColor(R.color.red));
                           buildingId.setError("Building does not exist.");
                       }
                   }
               }
               catch (Exception e){
                   Log.d("check", "onCreateView: " + e.getMessage());
               }
           }
       }


        // checking whether building id is valid or not
        buildingId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int building_id;

                try {
                    if (buildingId.getText().length() != 0) {

                        building_id = Integer.parseInt(buildingId.getText().toString());

                        if (new DatabaseHelper(getContext()).doesExistInBuildingTable(building_id)) {
                            buildingId.setTextColor(getResources().getColor(R.color.green));
                        }
                        else{
                            buildingId.setTextColor(getResources().getColor(R.color.red));
                            buildingId.setError("Building does not exist.");
                        }
                    }

                }
                catch(Exception e){
                    Toast.makeText(getContext(), "Invalid input.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

                    if (workerId.getText().length() == 0) {
                        workerId.setError("Id is required.");
                        Toast.makeText(getContext(), "Id is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (buildingId.getText().length() == 0) {
                        buildingId.setError("Id is required.");
                        Toast.makeText(getContext(), "Id is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (workerName.getText().length() == 0) {
                        workerName.setError("Name is required.");
                        Toast.makeText(getContext(), "Name is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (salary.getText().length() == 0) {
                        salary.setError("Salary is required.");
                        Toast.makeText(getContext(), "Salary is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (job.getText().length() == 0) {
                        job.setError("Name is required.");
                        Toast.makeText(getContext(), "Name is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int wId = Integer.parseInt(workerId.getText().toString());
                    int bId = Integer.parseInt(buildingId.getText().toString());
                    String wName = workerName.getText().toString();
                    String wJob = job.getText().toString();
                    int wSalary = Integer.parseInt(salary.getText().toString());

                    if(!databaseHelper.doesExistInBuildingTable(Integer.parseInt(buildingId.getText().toString()))) {
                        Toast.makeText(getContext(), "Invalid Building Id", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    WorkerModel workerRecord = new WorkerModel(wId, bId, wName, wJob, wSalary);
                    databaseHelper.insertInToWorkersTable(workerRecord);
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), "Fill the required fields properly.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}