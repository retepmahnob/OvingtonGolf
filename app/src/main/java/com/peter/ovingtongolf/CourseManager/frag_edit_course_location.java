package com.peter.ovingtongolf.CourseManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.ovingtongolf.Data.CourseItem;
import com.peter.ovingtongolf.R;

/**
 * Created by peter on 18/02/15.
 */
public class frag_edit_course_location extends Fragment {

    private CourseItem currentCourse;
    public static frag_edit_course_location newInstance(CourseItem currentCourse){

        frag_edit_course_location courseLocation = new frag_edit_course_location();
        Bundle args = new Bundle();
        courseLocation.setArguments(args);
        return courseLocation;
    }
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        if (savedInstance != null){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.frag_edit_course_location, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(this.getClass().getName(), "onStart called ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(this.getClass().getName(), "onResume called ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(this.getClass().getName(), "onSaveInstanceState called ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(this.getClass().getName(), "onPause called ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(this.getClass().getName(), "onStop called ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.getClass().getName(), "onDestroy called ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(this.getClass().getName(), "onDetach called ");
    }
}
