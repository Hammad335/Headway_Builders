package com.codewithhamad.headwaybuilders.analyst.analysteditfrag;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.codewithhamad.headwaybuilders.R;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;


public class AnalystEditFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_analyst_edit, container, false);


        // init views;
        viewPager= view.findViewById(R.id.editFragViewPager);
        tabLayout= view.findViewById(R.id.editFragTabLayout);

        viewPagerAdapter= new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new EditBuildingFragment(), "Edit Building");
        viewPagerAdapter.addFragment(new EditWorkerFragment(), "Edit Worker");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // when editBuildingBtn clicked from buildingDetailsFragment
        Bundle bundle= this.getArguments();
        if(bundle!=null){

            int buildingId= bundle.getInt("id");
            if(buildingId!=0 && buildingId != -1){
                bundle.putInt("id", buildingId);
                viewPagerAdapter.setArgument(bundle);
            }
        }



        return view;
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

        public void setArgument(Bundle bundle) {
            fragments.get(0).setArguments(bundle);
        }
    }



}