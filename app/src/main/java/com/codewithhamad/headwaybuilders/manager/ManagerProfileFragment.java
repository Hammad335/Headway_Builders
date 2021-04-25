package com.codewithhamad.headwaybuilders.manager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codewithhamad.headwaybuilders.R;

public class ManagerProfileFragment extends Fragment {

    TextView userName, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manager_profile, container, false);


        userName= view.findViewById(R.id.managerUserNameProfileFrag);
        password= view.findViewById(R.id.managerPassProfileFrag);


        return view;
    }
}