package com.codewithhamad.headwaybuilders.manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.HomeFragment;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class ManagerActivity extends AppCompatActivity {

    ReadableBottomBar readableBottomBar;
    FrameLayout frameLayout; // container for fragments used in this activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        // initializing views
        readableBottomBar= findViewById(R.id.managerBottomNav);
        frameLayout= findViewById(R.id.managerContainerFrameLayout);

        // by default: home fragment
        FragmentTransaction homeTrans= getSupportFragmentManager().beginTransaction();
        homeTrans.replace(R.id.managerContainerFrameLayout, new HomeFragment());
        homeTrans.commit();

        readableBottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
//						transaction.setCustomAnimations(R.anim.fadein,R.anim.sample_anim,R.anim.fadein,R.anim.sample_anim);
                        transaction.replace(R.id.managerContainerFrameLayout, new HomeFragment());
                        break;

                    case 1:
//						transaction.setCustomAnimations(R.anim.fadein,R.anim.sample_anim,R.anim.fadein,R.anim.sample_anim);
                        transaction.replace(R.id.managerContainerFrameLayout, new ManagerAddFragment());
                        break;

                    case 2:
//						transaction.setCustomAnimations(R.anim.fadein,R.anim.sample_anim,R.anim.fadein,R.anim.sample_anim);
                        transaction.replace(R.id.managerContainerFrameLayout, new ManagerProfileFragment());
                        break;

                }
                transaction.commit();
            }
        });
    }
}