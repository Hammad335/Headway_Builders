package com.codewithhamad.headwaybuilders.analyst.analysteditfrag;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.WorkerModel;
import org.jetbrains.annotations.NotNull;

public class EditWorkerFragment extends Fragment {
    private EditText workerId, buildingId, workerName, salary, job;
    private Button updateBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_worker, container, false);

        // init views
        workerId = view.findViewById(R.id.editWorkerWorkerId);
        buildingId = view.findViewById(R.id.editWorkerBuildingId);
        workerName = view.findViewById(R.id.editWorkerWorkerName);
        salary = view.findViewById(R.id.editWorkerSalary);
        job = view.findViewById(R.id.editWorkerJob);
        updateBtn = view.findViewById(R.id.updtWorkerAddBtn);

        // disabling views at the start except workerId
        disableViews();

        // setting data to the views retrieved from worker table based on workerId
        workerId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    if(workerId.getText().length() != 0){

                        int wId= Integer.parseInt(workerId.getText().toString());

                        if(new DatabaseHelper(getContext()).doesExistInWorkerTable(wId)){

                            workerId.setTextColor(getResources().getColor(R.color.green));

                            WorkerModel workerModel = new DatabaseHelper(getContext()).getByIdFromWorkerTable(wId);

                            if(workerModel != null) {
                                enableViews();
                                setDataToTheViews(workerModel);
                            }
                            else{
                                Toast.makeText(getContext(), "workerModel is null", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            workerId.setTextColor(getResources().getColor(R.color.red));
                            workerId.setError("Worker does not exist.");

                            // disable views
                            disableViews();
                        }
                    }
                    else{
                        // disable views
                        disableViews();
                    }
                }
                catch(Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buildingId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(buildingId.getText().length()!=0) {
                    if (!(new DatabaseHelper(getContext()).doesExistInBuildingTable(Integer.parseInt(buildingId.getText().toString())))) {
                        buildingId.setTextColor(getResources().getColor(R.color.red));
                        buildingId.setError("Invalid Id");
                    } else
                        buildingId.setTextColor(getResources().getColor(R.color.green));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // setting onClickListener to the update button
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // temp vars
                int wId, bId, wSal;
                String wName, wJob;

                try {
                    // validating data

                    if(workerId.getText().length()==0){
                        workerId.setError("Id is required.");
                        Toast.makeText(getContext(), "Id is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(buildingId.getText().length()==0){
                        buildingId.setError("Id is required.");
                        Toast.makeText(getContext(), "Id is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(buildingId.getText().length() != 0 &&
                            !(new DatabaseHelper(getContext()).doesExistInBuildingTable(Integer.parseInt(buildingId.getText().toString())))){
                        Toast.makeText(getContext(), "Building does not exist", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(workerName.getText().length()==0){
                        workerName.setError("Name is required.");
                        Toast.makeText(getContext(), "Name is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(salary.getText().length()==0){
                        salary.setError("Salary is required.");
                        Toast.makeText(getContext(), "Salary is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(job.getText().length()==0){
                        job.setError("Job is required.");
                        Toast.makeText(getContext(), "Job is required field.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    wId= Integer.parseInt(workerId.getText().toString());
                    bId = Integer.parseInt(buildingId.getText().toString());
                    wName= workerName.getText().toString();
                    wSal= Integer.parseInt(salary.getText().toString());
                    wJob= job.getText().toString();

                    WorkerModel record= new WorkerModel(wId, bId, wName, wJob, wSal);

                    // updating the record
                    new DatabaseHelper(getContext()).updateRecordInToWorkerTable(record);
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "Fill the required fields properly.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void enableViews() {
        buildingId.setInputType(InputType.TYPE_CLASS_NUMBER);
        buildingId.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        workerName.setInputType(InputType.TYPE_CLASS_TEXT);
        workerName.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        salary.setInputType(InputType.TYPE_CLASS_NUMBER);
        salary.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        job.setInputType(InputType.TYPE_CLASS_TEXT);
        job.setBackground(getResources().getDrawable(R.drawable.edittext_border_color));

        updateBtn.setEnabled(true);
    }

    private void disableViews() {

        buildingId.setText("");
        buildingId.setInputType(InputType.TYPE_NULL);
        buildingId.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        workerName.setText("");
        workerName.setInputType(InputType.TYPE_NULL);
        workerName.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        salary.setText("");
        salary.setInputType(InputType.TYPE_NULL);
        salary.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        job.setText("");
        job.setInputType(InputType.TYPE_NULL);
        job.setBackground(getResources().getDrawable(R.drawable.edittext_border_color_disabled));

        updateBtn.setEnabled(false);
    }

    private void setDataToTheViews(@NotNull WorkerModel workerModel) {

        buildingId.setText(String.valueOf(workerModel.getBuildingId()));

        workerName.setText(workerModel.getWorkerName());

        salary.setText(String.valueOf(workerModel.getSalary()));

        job.setText(workerModel.getJob());

    }
}
