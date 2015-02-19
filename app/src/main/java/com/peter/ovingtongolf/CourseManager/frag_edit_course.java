package com.peter.ovingtongolf.CourseManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.utilities.SlidingTabLayout;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


/**
 * Created by peter on 16/02/15.
 */
public class frag_edit_course extends Fragment {

    private ViewPager viewPager;
    private SlidingTabLayout tabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.frag_edit_course, container, false);
        CoursePagerAdapter adapter = new CoursePagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerEditCourse);
        tabHost = (SlidingTabLayout) view.findViewById(R.id.tabHostEditCourse);

        viewPager.setAdapter(adapter);
        tabHost.setViewPager(viewPager);

        return view;
    }

    private class CoursePagerAdapter extends FragmentPagerAdapter {

        private String[] TabTiles = {"Details", "Location", "Holes", "History"};

        public CoursePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new frag_edit_course_details();
                case 1:
                    return frag_edit_course_location.newInstance();
                case 2:
                    return new frag_edit_course_details();
                default:
                    return new frag_edit_course_details();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return TabTiles[position];
        }
    }
}
