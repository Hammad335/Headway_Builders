package com.codewithhamad.headwaybuilders.analyst.analystaddfrag;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.codewithhamad.headwaybuilders.R;
import com.codewithhamad.headwaybuilders.analyst.AnalystActivity;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;


public class AnalystAddFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;

    public static int buildingId= -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_analyst_add, container, false);


        // init views;
        viewPager= view.findViewById(R.id.addFragViewPager);
        tabLayout= view.findViewById(R.id.addFragTabLayout);



        viewPagerAdapter= new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new AddBuildingFragment(), "Add Building");
        viewPagerAdapter.addFragment(new AddWorkerFragment(), "Add Worker");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        // when addWorkerBtn clicked from buildingDetailsFragment
        Bundle bundle= this.getArguments();
        if(bundle!=null){

            int buildingId= bundle.getInt("id");
            if(buildingId!=0 && buildingId != -1){
                viewPager.setCurrentItem(1);

                bundle.putInt("id", buildingId);
                viewPagerAdapter.setArgument(bundle);
            }
        }

        return view;
    }

    public static int getBuildingId(){
        return buildingId;
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter{

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

        public void setArgument(Bundle bundle){
            fragments.get(1).setArguments(bundle);
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

