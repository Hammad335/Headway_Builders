package com.codewithhamad.headwaybuilders.analyst;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codewithhamad.headwaybuilders.R;


public class AnalystProfileFragment extends Fragment {

    TextView userName, pass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_analyst_profile, container, false);

        userName= view.findViewById(R.id.analystUserNameProfileFrag);
        pass= view.findViewById(R.id.analystPassProfileFrag);


        Bundle bundle= this.getArguments();
        if(bundle!=null){
            String uNmae= bundle.getString("userName");
            String password= bundle.getString("pass");

            userName.setText(uNmae);
            pass.setText(password);
        }




        return view;
    }
}