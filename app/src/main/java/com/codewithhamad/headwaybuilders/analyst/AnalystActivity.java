package com.codewithhamad.headwaybuilders.analyst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.codewithhamad.headwaybuilders.HomeFragment;
import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.analyst.analystaddfrag.AnalystAddFragment;
import com.codewithhamad.headwaybuilders.analyst.analysteditfrag.AnalystEditFragment;
import com.codewithhamad.headwaybuilders.main.AnalystLoginFragment;
import com.codewithhamad.headwaybuilders.main.MainActivity;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class AnalystActivity extends AppCompatActivity {

	public static ReadableBottomBar readableBottomBar;
	FrameLayout frameLayout; // container for fragments used in this activity

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_analyst);

		// initializing views
		readableBottomBar= findViewById(R.id.analystBottomNav);
		frameLayout= findViewById(R.id.analystContainerFrameLayout);

		// by default: home fragment
		FragmentTransaction homeTrans= getSupportFragmentManager().beginTransaction();
		homeTrans.replace(R.id.analystContainerFrameLayout, new HomeFragment());
		homeTrans.commit();

		// setting onClickListener to bottomNavBar
		readableBottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
			@Override
			public void onItemSelected(int i) {
				FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
				switch (i){
					case 0:
//						transaction.setCustomAnimations(R.anim.fadein,R.anim.sample_anim,R.anim.fadein,R.anim.sample_anim);
						transaction.replace(R.id.analystContainerFrameLayout, new HomeFragment());
						break;

					case 1:
//						transaction.setCustomAnimations(R.anim.fadein,R.anim.sample_anim,R.anim.fadein,R.anim.sample_anim);
						transaction.replace(R.id.analystContainerFrameLayout, new AnalystAddFragment());
						break;

					case 2:
//						transaction.setCustomAnimations(R.anim.fadein,R.anim.sample_anim,R.anim.fadein,R.anim.sample_anim);
						transaction.replace(R.id.analystContainerFrameLayout, new AnalystEditFragment());
						break;

					case 3:
//						transaction.setCustomAnimations(R.anim.fadein,R.anim.sample_anim,R.anim.fadein,R.anim.sample_anim);
						AnalystProfileFragment analystProfileFragment= new AnalystProfileFragment();
						Bundle bundle= new Bundle();

						String userName= getIntent().getStringExtra("userName");
						String pass= getIntent().getStringExtra("pass");

						bundle.putString("userName", userName);
						bundle.putString("pass", pass);
						analystProfileFragment.setArguments(bundle);
						transaction.replace(R.id.analystContainerFrameLayout, analystProfileFragment);
						break;
				}
				transaction.commit();
			}
		});
	}

//	@Override
//	public void onBackPressed() {
//		Intent intent= new Intent(AnalystActivity.this, MainActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(intent);
//	}
}