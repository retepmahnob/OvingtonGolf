package com.peter.ovingtongolf.CourseManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peter.ovingtongolf.R;

/**
 * Created by peter on 18/02/15.
 */
public class frag_edit_course_location extends Fragment {

    public static frag_edit_course_location newInstance(){

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
}
