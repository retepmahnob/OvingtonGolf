package com.peter.ovingtongolf.CourseManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.ovingtongolf.Data.CourseItem;
import com.peter.ovingtongolf.R;
import com.peter.ovingtongolf.databaseProvider.sqlcontractGolf;
import com.peter.ovingtongolf.utilities.SlidingTabLayout;


/**
 * Created by peter on 16/02/15.
 */
public class frag_edit_course extends Fragment implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private SlidingTabLayout tabHost;

    private frag_edit_course_details courseDetails;
    private frag_edit_course_location courseLocation;
    private frag_list_course_holes courseHoles;
    private CourseItem currentCourse;

    private CoursePagerAdapter adapter;

    public void setCurrentCourse (String id, CourseItem currentCourse){
        this.currentCourse = currentCourse;

        if (courseDetails!=null){
            courseDetails.setCurrentCourse(id, currentCourse);
        }
        if (courseHoles!=null)
            courseHoles.SetCurrentCourseId(id);

        adapter.pageCount = 5;
        adapter.notifyDataSetChanged();
        tabHost.setViewPager(viewPager);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.frag_edit_course, container, false);
        adapter = new CoursePagerAdapter(getActivity().getSupportFragmentManager());

        viewPager = (ViewPager) view.findViewById(R.id.viewPagerEditCourse);

        tabHost = (SlidingTabLayout) view.findViewById(R.id.tabHostEditCourse);

        viewPager.setAdapter(adapter);
        tabHost.setViewPager(viewPager);

        tabHost.setOnPageChangeListener(this);


        return view;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d(this.getClass().getName(), "onPageSelected " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class CoursePagerAdapter extends FragmentPagerAdapter {

        private String[] TabTiles = {"Details", "Location", "Holes", "History", "Another 1", "Another 2", "Another 3", "Another 4"};

        public int pageCount = 3;
        public CoursePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    courseDetails = frag_edit_course_details.newInstance(currentCourse);
                    return courseDetails;
                case 1:
                    courseLocation = frag_edit_course_location.newInstance(currentCourse);
                    return courseLocation;
                case 2:
                    courseHoles = frag_list_course_holes.newInstance(currentCourse);
                    return courseHoles;
                default:
                    return new frag_edit_course_details();
            }
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @Override
        public CharSequence getPageTitle(int position){
            return TabTiles[position];
        }
    }
}
