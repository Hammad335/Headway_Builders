package com.codewithhamad.headwaybuilders.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.widget.Toast;
import com.codewithhamad.headwaybuilders.R;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

  private ViewPager viewPager;
  private TabLayout tabLayout;
  private ViewPagerAdapter viewPagerAdapter;

  private long backPressedTime;
  private Toast backToast;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // init views;
    viewPager= findViewById(R.id.mainActivityViewPager);
    tabLayout= findViewById(R.id.mainActivityTabLayout);

    viewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager());
    viewPagerAdapter.addFragment(new ManagerLoginFragment(), "Manager");
    viewPagerAdapter.addFragment(new AnalystLoginFragment(), "Analyst");

    viewPager.setAdapter(viewPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);

  }

  public static class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
      super(fm);
      this.fragments= new ArrayList<>();
      this.titles= new ArrayList<>();
    }

    public void addFragment(Fragment fragment, String title){
      fragments.add(fragment);
      titles.add(title);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
      return fragments.get(position);
    }

    @Override
    public int getCount() {
      return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
      return titles.get(position);
    }
  }


  @Override
  public void onBackPressed() {
    if(backPressedTime+2000>System.currentTimeMillis()){
      backToast.cancel();
      super.onBackPressed();
      return;
    }
    else{
      backToast= Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
      backToast.show();
    }
    backPressedTime= System.currentTimeMillis();
  }
}