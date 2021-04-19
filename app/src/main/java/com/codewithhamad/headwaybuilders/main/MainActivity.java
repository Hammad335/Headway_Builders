package com.codewithhamad.headwaybuilders.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.analyst.analystaddfrag.AddBuildingFragment;
import com.codewithhamad.headwaybuilders.analyst.analystaddfrag.AddWorkerFragment;
import com.codewithhamad.headwaybuilders.analyst.analystaddfrag.AnalystAddFragment;
import com.google.android.material.tabs.TabLayout;
import com.iammert.library.readablebottombar.ReadableBottomBar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

//  ReadableBottomBar readableBottomBar;
//  FrameLayout frameLayout; // container for fragments used in this activity

  private ViewPager viewPager;
  private TabLayout tabLayout;
  private ViewPagerAdapter viewPagerAdapter;

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
}