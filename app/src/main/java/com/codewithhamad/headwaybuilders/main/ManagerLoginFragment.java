package com.codewithhamad.headwaybuilders.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.analyst.AnalystActivity;
import com.codewithhamad.headwaybuilders.manager.ManagerActivity;


public class ManagerLoginFragment extends Fragment {
    private TextView welcomeText;
    private EditText userNameEditTxt, passwordEditTxt;
    private ImageView userNameImageView, passwordImageView, logo;
    private Button signInBtn;

    private Animation fadeIn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manager_login, container, false);

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

//                if (userNameEditTxt.getText().length() == 0){
//                    userNameEditTxt.setError("Name is required.");
//                    return;
//                }
//                else if(passwordEditTxt.getText().length()==0) {
//                    passwordEditTxt.setError("Password is required.");
//                    return;
//                }


                startActivity(new Intent(getActivity(), ManagerActivity.class));
            }
        });

        return view;
    }
}