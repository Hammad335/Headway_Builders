package com.codewithhamad.headwaybuilders.manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.HomeFragment;
import com.codewithhamad.headwaybuilders.main.MainActivity;
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

    @Override
    public void onBackPressed() {
        AlertDialog deleteDialog = new AlertDialog.Builder(this)
                .setTitle("Return Message")
                .setMessage("Are you sure you want to go back to login screen ?")
                .setIcon(R.drawable.ic_ask)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent= new Intent(ManagerActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        deleteDialog.show();


    }
}