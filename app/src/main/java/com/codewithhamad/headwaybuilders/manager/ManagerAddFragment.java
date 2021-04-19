package com.codewithhamad.headwaybuilders.manager;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.adapters.AnalystAdapter;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.AnalystLoginModel;

import java.util.ArrayList;


public class ManagerAddFragment extends Fragment {

    RecyclerView recyclerView;
    Button addNewAnalystBtn;
    AnalystAdapter analystAdapter;
    ArrayList<AnalystLoginModel> allAnalysts= new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manager_add, container, false);

        // init views
        recyclerView= view.findViewById(R.id.analystsRecyclerView);
        addNewAnalystBtn= view.findViewById(R.id.addNewAnalystBtn);

        try{
            allAnalysts= new DatabaseHelper(getContext()).getAllFromAnalystsTable();

            if(allAnalysts != null){
                analystAdapter= new AnalystAdapter(getContext(), allAnalysts);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(analystAdapter);
            }
        }
        catch(Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        addNewAnalystBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view1= LayoutInflater.from(getContext()).inflate(R.layout.sample_add_analyst_dialog, null);

                AlertDialog addAnalystDialog= new AlertDialog.Builder(getContext())
                        .setView(view1).create();
                addAnalystDialog.setCancelable(false);
                addAnalystDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
                addAnalystDialog.show();

                EditText analystUserName, analystPass;
                Button addAnalystBtn, cancelBtn;
                analystUserName= view1.findViewById(R.id.add_anlayst_username);
                analystPass= view1.findViewById(R.id.add_anlayst_password);
                addAnalystBtn= view1.findViewById(R.id.addNewAnalystToDatabaseBtn);
                cancelBtn= view1.findViewById(R.id.cancelBtn);

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addAnalystDialog.dismiss();
                    }
                });

                addAnalystBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(analystUserName.getText().length()==0){
                            analystUserName.setError("user name is required.");
                            return;
                        }
                        else if(analystPass.getText().length()==0){
                            analystPass.setError("password is required");
                            return;
                        }

                        try {
                            if (new DatabaseHelper(getContext()).doesExistInAnalystsTable(analystUserName.getText().toString())) {
                                analystUserName.setError("analyst already exist.");
                                return;
                            }
                            else{

                                String analyst_user_name = analystUserName.getText().toString();
                                String analyst_pass = analystPass.getText().toString();

                                AnalystLoginModel analystLoginModel = new AnalystLoginModel(analyst_user_name, analyst_pass);
                                new DatabaseHelper(getContext()).insertInToAnalystsTable(analystLoginModel);

                                analystAdapter.setAnalysts(new DatabaseHelper(getContext()).getAllFromAnalystsTable());

                                addAnalystDialog.dismiss();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getContext(), "Failed to add analyst to database.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        return view;
    }
}