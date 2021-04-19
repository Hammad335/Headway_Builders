package com.codewithhamad.headwaybuilders.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.analyst.AnalystActivity;
import com.codewithhamad.headwaybuilders.databasehelper.DatabaseHelper;
import com.codewithhamad.headwaybuilders.models.AnalystLoginModel;


public class AnalystLoginFragment extends Fragment {

    private TextView welcomeText;
    private EditText userNameEditTxt, passwordEditTxt;
    private ImageView userNameImageView, passwordImageView, logo;
    private Button signInBtn;

    private Animation fadeIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_analyst_login, container, false);

        // init views
        userNameEditTxt = view.findViewById(R.id.USERNAME);
        passwordEditTxt = view.findViewById(R.id.PASSWORD);
        userNameImageView = view.findViewById(R.id.imageView9);
        passwordImageView = view.findViewById(R.id.imageView8);
        logo = view.findViewById(R.id.imageView2);
        signInBtn = view.findViewById(R.id.signin);
        welcomeText = view.findViewById(R.id.textView3);

        fadeIn = AnimationUtils.loadAnimation(getContext(),R.anim.fadein);

        // setting anims
        userNameEditTxt.startAnimation(fadeIn);
        passwordEditTxt.startAnimation(fadeIn);
        userNameImageView.startAnimation(fadeIn);
        passwordImageView.startAnimation(fadeIn);
//        logo.startAnimation(fadeIn);
        signInBtn.startAnimation(fadeIn);
//        welcomeText.startAnimation(fadeIn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName= userNameEditTxt.getText().toString();
                String pass= passwordEditTxt.getText().toString();
                if(userName.length()==0){
                    userNameEditTxt.setError("User name is required.");
                    return;
                }
                else if(pass.length()==0){
                    passwordEditTxt.setError("Password is required.");
                    return;
                }

                try{

                    AnalystLoginModel analystLoginModel= new AnalystLoginModel(userName, pass);

                    if (new DatabaseHelper(getContext()).doesExistInAnalystsTable(analystLoginModel)){

                        Intent intent= new Intent(getActivity(), AnalystActivity.class);
                        intent.putExtra("userName", analystLoginModel.getName());
                        intent.putExtra("pass", analystLoginModel.getPassword());
                        startActivity(intent);
//                        startActivity(new Intent(getActivity(), AnalystActivity.class));
                    }
                    else
                        Toast.makeText(getContext(), "Invalid user name or password.", Toast.LENGTH_SHORT).show();

                }
                catch(Exception e){
                    Log.d("log", "onClick: " + e.getMessage());
                }
            }
        });



        return view;
    }
}